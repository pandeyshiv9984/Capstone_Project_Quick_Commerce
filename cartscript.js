const API_URL = "http://localhost:3000"; // Replace with your backend URL

// Fetch products from backend
async function fetchProducts() {
  const res = await fetch(`${API_URL}/products`);
  return await res.json();
}

// Fetch cart from backend
async function fetchCart() {
  const res = await fetch(`${API_URL}/cart`);
  return await res.json();
}

// Render products
async function renderProducts() {
  const products = await fetchProducts();
  const productList = document.getElementById("productList");
  productList.innerHTML = "";

  products.forEach(product => {
    const productDiv = document.createElement("div");
    productDiv.classList.add("product");
    productDiv.innerHTML = `
      <img src="${product.image}" alt="${product.name}">
      <h4>${product.name}</h4>
      <p>₹${product.price}</p>
      <button onclick="addToCart(${product.id})">Add to Cart</button>
    `;
    productList.appendChild(productDiv);
  });
}

// Render cart
async function renderCart() {
  const cart = await fetchCart();
  const cartItemsDiv = document.getElementById("cartItems");
  cartItemsDiv.innerHTML = "";

  let total = 0;

  cart.forEach(item => {
    const itemSubtotal = item.price * item.quantity;
    total += itemSubtotal;

    const cartItem = document.createElement("div");
    cartItem.classList.add("cart-item");
    cartItem.innerHTML = `
      <img src="${item.image}" alt="${item.name}">
      <div class="item-info">
        <h4>${item.name}</h4>
        <p>Price: ₹${item.price}</p>
        <p><strong>Subtotal: ₹${itemSubtotal}</strong></p>
      </div>
      <div class="item-actions">
        <button onclick="updateQuantity(${item.id}, ${item.quantity - 1})">-</button>
        <span>${item.quantity}</span>
        <button onclick="updateQuantity(${item.id}, ${item.quantity + 1})">+</button>
        <button class="remove-btn" onclick="removeItem(${item.id})">Remove</button>
      </div>
    `;
    cartItemsDiv.appendChild(cartItem);
  });

  document.getElementById("cartTotal").innerText = total;
}

// Add item to cart (with backend sync)
async function addToCart(productId) {
  const products = await fetchProducts();
  const product = products.find(p => p.id === productId);

  // Check if item already in cart
  const cart = await fetchCart();
  const existingItem = cart.find(item => item.productId === productId);

  if (existingItem) {
    await updateQuantity(existingItem.id, existingItem.quantity + 1);
  } else {
    await fetch(`${API_URL}/cart`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...product, productId: product.id, quantity: 1 })
    });
  }
  renderCart();
}

// Update quantity
async function updateQuantity(cartId, newQuantity) {
  if (newQuantity <= 0) {
    await removeItem(cartId);
    return;
  }

  await fetch(`${API_URL}/cart/${cartId}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ quantity: newQuantity })
  });

  renderCart();
}

// Remove item
async function removeItem(cartId) {
  await fetch(`${API_URL}/cart/${cartId}`, { method: "DELETE" });
  renderCart();
}

// Init
renderProducts();
renderCart();


// Checkout function
async function checkout() {
  const cart = await fetchCart();

  if (cart.length === 0) {
    alert("Your cart is empty!");
    return;
  }

  const order = {
    items: cart.map(item => ({
      productId: item.productId,
      name: item.name,
      price: item.price,
      quantity: item.quantity,
      subtotal: item.price * item.quantity
    })),
    total: cart.reduce((sum, item) => sum + item.price * item.quantity, 0),
    createdAt: new Date().toISOString()
  };

  const res = await fetch(`${API_URL}/orders`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(order)
  });

  if (res.ok) {
    // Clear the cart
    for (const item of cart) {
      await removeItem(item.id);
    }

    // Show modal instead of alert
    document.getElementById("successModal").style.display = "block";
    renderCart();
  } else {
    alert("❌ Failed to place order. Please try again.");
  }
}

// --- Modal handling ---
document.getElementById("closeModal").onclick = function() {
  document.getElementById("successModal").style.display = "none";
};

document.getElementById("okBtn").onclick = function() {
  document.getElementById("successModal").style.display = "none";
};

// Close modal when clicking outside content
window.onclick = function(event) {
  const modal = document.getElementById("successModal");
  if (event.target === modal) {
    modal.style.display = "none";
  }
};

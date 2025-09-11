const togglePassword = document.getElementById('togglePassword');
const passwordInput = document.getElementById('password');

togglePassword.addEventListener('click', function () {
  const type = passwordInput.type === "password" ? "text" : "password";
  passwordInput.type = type;
  this.classList.toggle('active');
  this.innerHTML = type === "password" 
  ? '<i class="fa fa-eye"></i>' 
  : '<i class="fa fa-eye-slash"></i>';

});

document.getElementById('loginForm').addEventListener('submit', function(e) {
  e.preventDefault();
  const identifier = document.getElementById('identifier').value.trim();
  const phoneRegex = /^\d{10}$/; 
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  if (!phoneRegex.test(identifier) && !emailRegex.test(identifier)) {
    alert("Please enter a valid 10-digit phone number or a valid email address.");
    return;
  }
  alert("Login functionality goes here!");
});

document.querySelectorAll('.card').forEach(card => {
    let qtyInput = card.querySelector('.qty');
    let plusBtn = card.querySelector('.plus');
    let minusBtn = card.querySelector('.minus');

    plusBtn.addEventListener('click', () => {
      qtyInput.value = parseInt(qtyInput.value) + 1;
    });

    minusBtn.addEventListener('click', () => {
      if (parseInt(qtyInput.value) > 1) {
        qtyInput.value = parseInt(qtyInput.value) - 1;
      }
    });
  });
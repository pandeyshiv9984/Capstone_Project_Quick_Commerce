document.querySelectorAll('.toggle-password').forEach(btn => {
  btn.addEventListener('click', function() {
    const targetId = this.getAttribute('data-target');
    const input = document.getElementById(targetId);

    if (input.type === "password") {
      input.type = "text";
      this.innerHTML = '<i class="fa fa-eye-slash"></i>';
    } else {
      input.type = "password";
      this.innerHTML = '<i class="fa fa-eye"></i>';
    }
  });
});

const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const confirmMessage = document.getElementById('confirmMessage');
const globalMessage = document.getElementById('message'); 

confirmPasswordInput.addEventListener('input', function () {
  if (confirmPasswordInput.value === "") {
    confirmMessage.textContent = "";
    return;
  }

  if (confirmPasswordInput.value !== passwordInput.value) {
    confirmMessage.textContent = "Passwords do not match.";
    confirmMessage.style.color = "red";
  } else {
    confirmMessage.textContent = "Passwords match!";
    confirmMessage.style.color = "green";
  }
});

document.getElementById('signupForm').addEventListener('submit', function(event) {
  event.preventDefault();

  if (passwordInput.value !== confirmPasswordInput.value) {
    confirmMessage.textContent = "Passwords do not match.";
    confirmMessage.style.color = "red";
    return;
  }

  confirmMessage.textContent = "";
  globalMessage.textContent = "Form data ready!";
  globalMessage.style.color = "green";
});

(function(){
  'use strict';

  const form = document.getElementById('complaintForm');
  const photoInput = document.getElementById('photo');
  const previewWrapper = document.getElementById('previewWrapper');
  const photoPreview = document.getElementById('photoPreview');
  const successModal = new bootstrap.Modal(document.getElementById('successModal'));
  const refIdElem = document.getElementById('refId');

  // Image preview
  photoInput.addEventListener('change', function(){
    const file = this.files && this.files[0];
    if(!file){
      previewWrapper.style.display = 'none';
      photoPreview.src = '';
      return;
    }
    if (!file.type.startsWith('image/')) {
      alert('Please upload an image file.');
      photoInput.value = '';
      previewWrapper.style.display = 'none';
      return;
    }
    const reader = new FileReader();
    reader.onload = e => {
      photoPreview.src = e.target.result;
      previewWrapper.style.display = 'block';
    };
    reader.readAsDataURL(file);
  });

  // Generate ref id
  function generateRefId(){
    const ts = Date.now().toString(36).toUpperCase().slice(-6);
    const rnd = Math.floor(Math.random()*900 + 100).toString();
    return `BKT-${ts}-${rnd}`;
  }

  // Form submission
  form.addEventListener('submit', function(event){
    event.preventDefault();
    event.stopPropagation();

    if (!form.checkValidity()){
      form.classList.add('was-validated');
      return;
    }

    const payload = {
      name: form.name.value.trim(),
      email: form.email.value.trim(),
      phone: form.phone.value.trim(),
      orderId: form.orderId.value.trim(),
      type: form.type.value,
      preferredResolution: form.preferredResolution.value,
      description: form.description.value.trim(),
      consent: !!form.querySelector('#consent').checked,
      timestamp: new Date().toISOString()
    };

    if (photoInput.files && photoInput.files[0]) {
      payload.imageName = photoInput.files[0].name;
    }

    const refId = generateRefId();
    refIdElem.textContent = refId;

    form.reset();
    form.classList.remove('was-validated');
    previewWrapper.style.display = 'none';

    successModal.show();

    console.log('Complaint payload (demo):', payload, 'refId:', refId);
  });

  // Reset preview on form reset
  form.addEventListener('reset', function(){
    setTimeout(() => {
      previewWrapper.style.display = 'none';
      photoPreview.src = '';
      form.classList.remove('was-validated');
    }, 0);
  });

})();

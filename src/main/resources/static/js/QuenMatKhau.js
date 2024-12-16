function validateEmail() {
    const email = document.getElementById('email').value;
    const emailError = document.getElementById('email-error');
    if (!email) {
        emailError.style.display = 'block';
    } else {
        emailError.style.display = 'none';
        document.getElementById('password-recovery').style.display = 'none';
        document.getElementById('otp-verification').style.display = 'block';
        document.getElementById('progress').style.width = '66%';
    }
}

function moveToNextBox(currentBox) {
    if (currentBox < 6) {
        const currentInput = document.getElementById(`otp-${currentBox}`);
        const nextInput = document.getElementById(`otp-${currentBox + 1}`);
        if (currentInput.value) {
            nextInput.focus();
        }
    }
}

function resendOtp() {
    alert('OTP đã được gửi lại. Vui lòng kiểm tra email của bạn.');
}

function confirmOtp() {
    const otpInputs = document.querySelectorAll('.otp-bx input');
    let otp = '';
    otpInputs.forEach(input => {
        otp += input.value;
    });
    if (otp.length === 6) {
        document.getElementById('otp-verification').style.display = 'none';
        document.getElementById('new-password').style.display = 'block';
        document.getElementById('progress').style.width = '100%';
    } else {
        document.getElementById('otp-error').textContent = 'Vui lòng nhập đầy đủ mã OTP.';
        document.getElementById('otp-error').style.display = 'block';
    }
}

function goBackToRecovery() {
    document.getElementById('otp-verification').style.display = 'none';
    document.getElementById('password-recovery').style.display = 'block';
    document.getElementById('progress').style.width = '33%';
}

function goBackToOtp() {
    document.getElementById('new-password').style.display = 'none';
    document.getElementById('otp-verification').style.display = 'block';
    document.getElementById('progress').style.width = '66%';
}


// Function to toggle password visibility for a specific input field
function togglePasswordVisibility(inputId) {
    const passwordField = document.getElementById(inputId);
    const eyeIcon = passwordField.nextElementSibling.querySelector('i');
    
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        eyeIcon.classList.remove('fa-eye-slash');
        eyeIcon.classList.add('fa-eye');
    } else {
        passwordField.type = 'password';
        eyeIcon.classList.remove('fa-eye');
        eyeIcon.classList.add('fa-eye-slash');
    }
}

// Function to toggle visibility for new password input
function toggleNewPasswordVisibility() {
    togglePasswordVisibility('new-password-input');
}

// Function to toggle visibility for confirm password input
function toggleConfirmPasswordVisibility() {
    togglePasswordVisibility('confirm-password-input');
}

function confirmNewPassword() {
	const newPassword = document.getElementById('new-password-input').value;
	    const confirmPassword = document.getElementById('confirm-password-input').value;
	    const lengthRequirement = document.getElementById('length-requirement');
	    const characterRequirement = document.getElementById('character-requirement');
	    const allowedCharacters = /[a-zA-Z0-9~.!@#$%^&*]/;

	    if (newPassword.length < 8 || newPassword.length > 20) {
	        lengthRequirement.style.color = 'red';
	    } else {
	        lengthRequirement.style.color = '#555';
	    }

	    if (!/[a-zA-Z]/.test(newPassword) || !/[0-9]/.test(newPassword) || !allowedCharacters.test(newPassword)) {
	        characterRequirement.style.color = 'red';
	    } else {
	        characterRequirement.style.color = '#555';
	    }

	    if (newPassword !== confirmPassword) {
	        alert('Mật khẩu xác nhận không khớp. Vui lòng nhập lại.');
	    } else if (newPassword.length >= 8 && newPassword.length <= 20 && /[a-zA-Z]/.test(newPassword) && /[0-9]/.test(newPassword) && allowedCharacters.test(newPassword)) {
	        alert('Mật khẩu của bạn đã được thay đổi thành công.');
	        // Redirect to login page or another appropriate action
	        window.location.href = '/dangky';
	    } else {
	        alert('Vui lòng kiểm tra lại các yêu cầu về mật khẩu.');
	    }
	}

	function goBackToHome() {
	    // Logic to go back to home or landing page
	    window.location.href = 'index';
	};
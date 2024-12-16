
document.addEventListener("DOMContentLoaded", () => {
    const textElement1 = document.getElementById("typing-text1");
    const textElement2 = document.getElementById("typing-text2");
    const text1 = "Chào mừng"; 
    const text2 = "Đăng ký ngay"; 

    let index1 = 0;
    let index2 = 0;

    function typeText1() {
        if (index1 < text1.length) {
            textElement1.innerHTML += text1.charAt(index1);
            index1++;
            setTimeout(typeText1, 200); 
        } else {
            setTimeout(() => {
                index1 = 0;
                textElement1.innerHTML = ""; 
                typeText1(); 
            }, 1000); 
        }
    }

    function typeText2() {
        if (index2 < text2.length) {
            textElement2.innerHTML += text2.charAt(index2);
            index2++;
            setTimeout(typeText2, 200);
        } else {
            setTimeout(() => {
                index2 = 0;
                textElement2.innerHTML = ""; 
                typeText2(); 
            }, 5000);
        }
    }

    typeText1(); 
    typeText2(); 
});


const registerButton = document.getElementById("register");
const loginButton = document.getElementById("login");
const container = document.getElementById("container");
const loginContainer = document.getElementsByClassName("login-container")[0]; // Access the first element directly

    // Thêm sự kiện cho nút đăng ký
    registerButton.addEventListener('click', (event) => {
        // Ngừng chuyển form nếu có lỗi
        if (!validateRegisterForm()) {
            event.preventDefault(); // Ngừng gửi form
            alert('Vui lòng điền đầy đủ thông tin hợp lệ.');
        } else {
            // Form hợp lệ, có thể thực hiện gửi form hoặc chuyển form nếu cần
            alert('Đăng ký thành công!');
        }
    });
	

// Xử lý chuyển đổi giữa form đăng nhập và đăng ký **
registerButton.addEventListener('click', () => {
		    container.classList.add("right-panel-active");
		    loginContainer.style.opacity = "0"; // Set opacity to 0 to make it invisible
	
});

loginButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
    loginContainer.style.opacity = "1"; // Reset opacity to 1 to make it visible again
});

document.getElementById("chk1").addEventListener("change", function() {
    if (this.checked) {
        console.log("Menu mở");
    } else {
        console.log("Menu đóng");
    }
});

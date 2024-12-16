const timerElement = document.getElementById("timer");
const successMessage = document.getElementById("successMessage");
var myInterval;
var timeRemaining;
var timerInterval;
function btntt() {
  timeRemaining = 300; // Reset thời gian
  timerInterval = setInterval(updateTimer, 1000);
  const selectedValue = document.querySelector('input[name="phuongThucTT"]:checked');
  
  if (selectedValue != null && selectedValue.value == "Chuyển khoản ngân hàng") {
    console.log(selectedValue)

    img.src = 'https://qr.sepay.vn/img?acc=0865854002&bank=MB&amount=' + sotien + '&des=' + noidung.innerText;
    let i = 0;
    // Tạo bản sao của console.error để phục hồi sau này
    const originalConsoleError = console.error;
    let currentDate = new Date();

    let timesubmit = currentDate.getFullYear().toString() + "-" + (currentDate.getMonth() + 1).toString() + "-" + currentDate.getDate().toString() + " " + currentDate.getHours().toString() + ":" + currentDate.getMinutes().toString() + ":" + currentDate.getSeconds().toString();
    myInterval = setInterval(async () => {
      try {
        const response = await fetch("http://localhost:8080/checktttc?sotien=" + sotien + "&noidung=" + noidung.innerText + "&timesubmit=" + timesubmit);
        const data = await response.json(); // Chuyển đổi phản hồi thành JSON
        if (data == true) {
          clearInterval(myInterval);  // Dừng Interval khi thanh toán thành công
          toast.className = "toast show";  // Hiển thị toast
          setTimeout(function () { 
            TTKH.submit();
          }, 5000);
        } else {
          i++;
        }
      } catch (error) {
        // Bỏ qua lỗi mà không log ra console
      } finally {
        // Khôi phục lại console.error sau khi fetch hoàn thành
        console.error = originalConsoleError;
      }

      // Dừng gọi API nếu vượt quá số lần thử cho phép (sau 5 phút)
      if (i > 59) {
        clearInterval(myInterval);
        alert("Thanh toán không thành công. Vui lòng thử lại sau.");
      }
    }, 5000);
  }
  else TTKH.submit();
}

function btnhuytt() {
  clearInterval(myInterval);
  clearInterval(timerInterval);
  timeRemaining = 300;
}
function selectPaymentMethod(element) {
  document.querySelectorAll('.payment-method').forEach(el => el.classList.remove('selected'));
  element.classList.add('selected');
}
function updateTimer() {
  const minutes = Math.floor(timeRemaining / 60);
  const seconds = timeRemaining % 60;

  // Cập nhật hiển thị đồng hồ
  timerElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

  // Giảm thời gian còn lại
  timeRemaining--;

  // Nếu đồng hồ đã hết thời gian
  if (timeRemaining < 0) {
    clearInterval(timerInterval);
  }
}
document.addEventListener("DOMContentLoaded", function () {
  const paymentModal = new bootstrap.Modal(document.getElementById("paymentModal"));
});

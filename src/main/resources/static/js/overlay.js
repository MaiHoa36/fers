const overlay = document.getElementById('overlay');
const form = document.getElementById('add-form');
let isSubmitting = false;

form.addEventListener('submit', function (event) {
    event.preventDefault(); // Ngăn chặn form submit mặc định

    if (isSubmitting) {
        return; // Nếu form đang được submit, không làm gì
    }

    isSubmitting = true; // Đánh dấu form đang được submit

    // Hiển thị overlay
    overlay.style.display = 'block';

    // Xử lý logic submit form
    form.submit().then(
        function () {
            overlay.style.display = 'none';
            isSubmitting = false;
        }
    );
});
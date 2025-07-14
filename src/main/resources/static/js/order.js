
    document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("orderForm");
    let isSubmitting = false;

    form.addEventListener("submit", function (e) {
    const selectedMethod = document.querySelector('input[name="paymentMethod"]:checked');
    if (!selectedMethod) {
    e.preventDefault();
    alert("Vui lòng chọn phương thức thanh toán!");
    return;
}

    if (isSubmitting) {
    e.preventDefault();
    return;
}

    isSubmitting = true;

    if (selectedMethod.value === "bank") {
    e.preventDefault(); // Ngăn submit mặc định

    // Chuyển action của form sang /payment/create
    form.action = "/payment/create";

    // Sau một chút delay để loading
    setTimeout(() => {
    form.submit();
}, 300); // hoặc dùng hiệu ứng loading tùy bạn
}

    // Nếu là COD, form sẽ submit bình thường (action = "/order/submit")
});
});


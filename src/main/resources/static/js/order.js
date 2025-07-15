document.addEventListener("DOMContentLoaded", function () {
    const orderForm = document.getElementById('orderForm');
    const bankPaymentForm = document.getElementById('bankPaymentForm');
    const submitBtn = document.querySelector('.submit-btn');

    // Handle form submission
    orderForm.addEventListener('submit', function(e) {
        const selectedPayment = document.querySelector('input[name="paymentMethod"]:checked');

        if (selectedPayment && selectedPayment.value === 'bank') {
            e.preventDefault();

            // Update bank payment form with current values
            updateBankFormData();

            // Submit the bank payment form
            bankPaymentForm.submit();
        }
        // For COD, let the form submit normally to /order/submit
    });

    function updateBankFormData() {
        // Get current form values
        const name = document.getElementById('username').value;
        const phone = document.getElementById('phone').value;
        const address = document.getElementById('address').value;
        const total = document.querySelector('input[name="total"]').value;

        // Update hidden form fields
        bankPaymentForm.querySelector('input[name="name"]').value = name;
        bankPaymentForm.querySelector('input[name="phoneNumber"]').value = phone;
        bankPaymentForm.querySelector('input[name="address"]').value = address;
        bankPaymentForm.querySelector('input[name="total"]').value = total;
        bankPaymentForm.querySelector('input[name="amount"]').value = total;

        // Update cart items in bank form
        const mainFormItems = orderForm.querySelectorAll('input[name*="items["]');
        mainFormItems.forEach(item => {
            const name = item.name;
            const value = item.value;
            const bankFormItem = bankPaymentForm.querySelector(`input[name="${name}"]`);
            if (bankFormItem) {
                bankFormItem.value = value;
            }
        });
    }
});
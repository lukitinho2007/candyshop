document.getElementById('add-product-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const productName = document.getElementById('product-name').value;
    const amount = parseInt(document.getElementById('product-amount').value);
    const password = document.getElementById('password').value;


    const response = await fetch('/store/product', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ password, name: productName, amount })
    });

    if (response.ok) {
        alert('Product added successfully!');

        document.getElementById('add-product-form').reset();
    } else {
        alert('Failed to add product. Check password or try again later.');
    }
});
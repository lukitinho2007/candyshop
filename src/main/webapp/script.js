document.addEventListener('DOMContentLoaded', function() {


async function fetchProducts() {
    try {
        const response = await fetch('/store');
        if (!response.ok) {
            throw new Error('Failed to fetch product list');
        }
        const data = await response.json();
        const productNames = data.productNames;
        const productsList = document.getElementById('products-list');


        productsList.innerHTML = '';

        productNames.forEach(name => {
            const row = document.createElement('tr');
            const productNameCell = document.createElement('td');
            productNameCell.textContent = name;
            const actionCell = document.createElement('td');
            const purchaseButton = document.createElement('button');
            purchaseButton.textContent = 'Purchase';
            purchaseButton.classList.add('purchase-button');
            purchaseButton.addEventListener('click', () => showProductPopup(name));
            actionCell.appendChild(purchaseButton);
            row.appendChild(productNameCell);
            row.appendChild(actionCell);
            productsList.appendChild(row);
        });
    } catch (error) {
        console.error(error);
    }
}

async function showProductPopup(productName) {
    const popup = document.getElementById('product-popup');
    const productInfo = document.getElementById('product-info');

    const response = await fetch('/store/product?name=' + encodeURIComponent(productName));
    const productData = await response.json();
    const price = productData.price;
    const available = productData.amount;

    productInfo.innerHTML = `
        <strong>${productName}</strong><br>
        Price: <strong>${price} GEL</strong><br>
        Available: <strong>${available}</strong><br>
        <button id="buy-now">Buy Now</button>
        <input type="number" value="0">
    `;


    popup.style.display = "block";


    document.getElementById('close-popup').addEventListener('click', function() {
        popup.style.display = "none";
    });


    document.getElementById('buy-now').addEventListener('click', function() {
        alert(`Buying ${productName}`);
    });
}
function loadCartItems() {
    const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const cartContainer = document.getElementById('cart-items');
    let subtotal = 0;
    cartContainer.innerHTML = cartItems.map(item => {
        const itemTotal = item.price * item.quantity;
        subtotal += itemTotal;
        return `
            <tr class="border-b">
                <td class="py-4">
                    <div class="flex items-center">
                        <img src="${item.image}" alt="${item.name}" class="h-16 w-16 object-cover rounded">
                        <div class="ml-4">
                            <h3 class="font-medium">${item.name}</h3>
                        </div>
                    </div>
                </td>
                <td class="text-center">
                    <div class="flex items-center justify-center">
                        <button onclick="updateQuantity(${item.id}, -1)" class="px-2">-</button>
                        <span class="mx-2">${item.quantity}</span>
                        <button onclick="updateQuantity(${item.id}, 1)" class="px-2">+</button>
                    </div>
                </td>
                <td class="text-right">$${item.price.toFixed(2)}</td>
                <td class="text-right">$${itemTotal.toFixed(2)}</td>
                <td class="text-right">
                    <button onclick="removeItem(${item.id})" class="text-red-500">
                        Eliminar
                    </button>
                </td>
            </tr>
        `;
    }).join('');
    updateCartTotals(subtotal);
}
function updateCartTotals(subtotal) {
    const tax = subtotal * 0.16;
    const total = subtotal + tax;

    document.getElementById('subtotal').textContent = `$${subtotal.toFixed(2)}`;
    document.getElementById('tax').textContent = `$${tax.toFixed(2)}`;
    document.getElementById('total').textContent = `$${total.toFixed(2)}`;
}
document.addEventListener('DOMContentLoaded', loadCartItems);
function loadOrderSummary() {
    const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const orderItemsContainer = document.getElementById('order-items');
    let subtotal = 0;

    orderItemsContainer.innerHTML = cartItems.map(item => {
        const itemTotal = item.price * item.quantity;
        subtotal += itemTotal;
        return `
            <div class="flex justify-between">
                <div>
                    <p class="font-medium">${item.name}</p>
                    <p class="text-sm text-gray-600">Cantidad: ${item.quantity}</p>
                </div>
                <span>$${itemTotal.toFixed(2)}</span>
            </div>
        `;
    }).join('');
    updateOrderTotals(subtotal);
}
function updateOrderTotals(subtotal) {
    const shipping = 10.00;
    const tax = subtotal * 0.16;
    const total = subtotal + shipping + tax;

    document.getElementById('subtotal').textContent = `$${subtotal.toFixed(2)}`;
    document.getElementById('shipping').textContent = `$${shipping.toFixed(2)}`;
    document.getElementById('tax').textContent = `$${tax.toFixed(2)}`;
    document.getElementById('total').textContent = `$${total.toFixed(2)}`;
}

function processOrder() {
    const form = document.getElementById('checkout-form');
    if (form.checkValidity()) {
        alert('¡Pedido realizado con éxito!');
        localStorage.removeItem('cartItems');
        window.location.href = '/order-confirmation';
    } else {
        form.reportValidity();
    }
}
document.addEventListener('DOMContentLoaded', loadOrderSummary);
async function fetchOrders() {
    try {
      const response = await fetch('/api/orders');
      const orders = await response.json();
  
      const ordersList = document.getElementById('ordersList');
      ordersList.innerHTML = '';
  
      orders.forEach(order => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td class="px-4 py-2 border">${order.id}</td>
          <td class="px-4 py-2 border">${order.customer.name}</td>
          <td class="px-4 py-2 border">${new Date(order.date).toLocaleString()}</td>
          <td class="px-4 py-2 border">${order.status}</td>
          <td class="px-4 py-2 border">$${order.total.toFixed(2)}</td>
          <td class="px-4 py-2 border">
            <a href="#" class="text-indigo-600 hover:text-indigo-900" onclick="viewOrder(${order.id})">View</a>
          </td>
        `;
        ordersList.appendChild(row);
      });
    } catch (error) {
      console.error('Error fetching orders:', error);
    }
  }
  let productCounter = 1;
  function addProduct() {
    const productsDiv = document.getElementById('products');
    const productDiv = document.createElement('div');
    productDiv.className = 'flex items-center space-x-2';
  
    const productSelect = document.createElement('select');
    productSelect.id = `product-${productCounter}`;
    productSelect.name = `product-${productCounter}`;
    productSelect.className = 'w-full border-gray-300 rounded-md shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50';
  
    // Fetch and populate product options
    fetchProducts(productSelect);
  
    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.className = 'text-red-600 hover:text-red-900';
    removeButton.textContent = 'Remove';
    removeButton.onclick = () => productDiv.remove();
  
    productDiv.appendChild(productSelect);
    productDiv.appendChild(removeButton);
    productsDiv.appendChild(productDiv);
  
    productCounter++;
  }
  
  // Fetch and display products in the select element
  async function fetchProducts(select) {
    try {
      const response = await fetch('/api/products');
      const products = await response.json();
  
      const defaultOption = document.createElement('option');
      defaultOption.value = '';
      defaultOption.textContent = 'Select a product';
      select.appendChild(defaultOption);
  
      products.forEach(product => {
        const option = document.createElement('option');
        option.value = product.id;
        option.textContent = product.name;
        select.appendChild(option);
      });
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  }
  
  // Handle new order form submission
  document.getElementById('newOrderForm').addEventListener('submit', async (event) => {
    event.preventDefault();
  
    const formData = new FormData(event.target);
    const orderData = {
      customer: formData.get('customer'),
      products: Array.from(formData.entries())
        .filter(([key, _]) => key.startsWith('product-'))
        .map(([_, value]) => parseInt(value))
    };
  
    try {
      const response = await fetch('/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderData)
      });
  
      if (response.ok) {
        alert('Order created successfully!');
        event.target.reset();
        fetchOrders();
      } else {
        alert('Error creating order. Please try again.');
      }
    } catch (error) {
      console.error('Error creating order:', error);
      alert('An error occurred while creating the order. Please try again later.');
    }
  });
  fetchOrders();
  document.addEventListener("DOMContentLoaded", function () {
    fetchUserProfile();
});

function fetchUserProfile() {
    fetch("/api/user/profile")
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener los datos del perfil");
            return response.json();
        })
        .then(data => {
            document.getElementById("firstname").textContent = data.firstname;
            document.getElementById("lastname").textContent = data.lastname;
            document.getElementById("email").textContent = data.email;
            document.getElementById("role").textContent = data.role;
        })
        .catch(error => console.error("Error:", error));
}

function logout() {
    fetch("/api/logout", {
        method: "POST",
    })
        .then(() => {
            window.location.href = "/login";
        })
        .catch(error => console.error("Error al cerrar sesión:", error));
}

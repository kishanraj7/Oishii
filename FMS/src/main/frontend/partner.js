/* =========================
   Protect Page
========================= */
const restaurantId = localStorage.getItem("restaurantId");

if (!restaurantId) {
    window.location.href = "partner-login.html";
}

/* =========================
   Global Variable
========================= */
let editingItemId = null;

/* =========================
   Section Switching
========================= */
function showSection(sectionId) {
    document.querySelectorAll(".section").forEach(sec =>
        sec.classList.remove("active")
    );
    document.getElementById(sectionId).classList.add("active");

    if (sectionId === "menu") {
        loadMenu();
    }
}

/* =========================
   Modal Handling
========================= */
function openModal() {
    editingItemId = null;

    document.getElementById("itemName").value = "";
    document.getElementById("itemPrice").value = "";
    document.getElementById("itemDescription").value = "";
    document.getElementById("itemImage").value = "";

    document.getElementById("itemModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("itemModal").style.display = "none";
}

/* =========================
   Logout
========================= */
function logout() {
    localStorage.removeItem("restaurantId");
    window.location.href = "partner-login.html";
}

/* =========================
   Load Profile
========================= */
async function loadProfile() {
    try {
        const response = await fetch(
            `http://localhost:8080/restaurants/find/${restaurantId}`
        );

        const data = await response.json();

        document.getElementById("restaurantId").innerText = data.rid;
        document.getElementById("restaurantName").innerText = data.restaurantName;
        document.getElementById("restaurantCuisine").innerText = data.cusine;
        document.getElementById("restaurantAddress").innerText = data.restaurantAddress;
        document.getElementById("restaurantPhone").innerText = data.restaurantPhoneNo;

        document.getElementById("profileImage").src =
            data.imageUrl
                ? `http://localhost:8080/uploads/${data.imageUrl}`
                : "https://via.placeholder.com/120?text=No+Image";

    } catch (error) {
        console.error(error);
    }
}

loadProfile();

/* =========================
   Update Profile
========================= */
function enableEdit() {
    document.querySelector(".profile-card").style.display = "none";
    document.getElementById("updateForm").style.display = "block";

    document.getElementById("updateName").value =
        document.getElementById("restaurantName").innerText;
    document.getElementById("updateCuisine").value =
        document.getElementById("restaurantCuisine").innerText;
    document.getElementById("updateAddress").value =
        document.getElementById("restaurantAddress").innerText;
    document.getElementById("updatePhone").value =
        document.getElementById("restaurantPhone").innerText;
}

function cancelUpdate() {
    document.getElementById("updateForm").style.display = "none";
    document.querySelector(".profile-card").style.display = "block";
}

async function submitUpdate() {
    const updatedData = new FormData();

    updatedData.append("restaurantName", document.getElementById("updateName").value);
    updatedData.append("cusine", document.getElementById("updateCuisine").value);
    updatedData.append("restaurantAddress", document.getElementById("updateAddress").value);
    updatedData.append("restaurantPhoneNo", document.getElementById("updatePhone").value);

    const imageFile = document.getElementById("updateImage").files[0];
    if (imageFile) {
        updatedData.append("image", imageFile);
    }

    try {
        const response = await fetch(
            `http://localhost:8080/restaurants/${restaurantId}`,
            {
                method: "PUT",
                body: updatedData
            }
        );

        if (response.ok) {
            alert("Profile updated successfully!");
            loadProfile();
            cancelUpdate();
        } else {
            alert("Failed to update profile.");
        }

    } catch (error) {
        console.error(error);
    }
}

/* =========================
   Load Menu
========================= */
async function loadMenu() {
    try {
        const response = await fetch(
            `http://localhost:8080/food-items/restaurant/${restaurantId}`
        );

        const items = await response.json();
        const menuList = document.getElementById("menuList");
        menuList.innerHTML = "";

        items.forEach(item => {
            const div = document.createElement("div");

            div.style = `
                background:white;
                padding:15px;
                border-radius:10px;
                box-shadow:0 5px 15px rgba(0,0,0,0.1);
            `;

            div.innerHTML = `
                <img src="${item.foodImage ?
                    `http://localhost:8080/uploads/${item.foodImage}` :
                    'https://via.placeholder.com/150'}"
                    style="width:100%;height:150px;object-fit:cover;border-radius:8px;margin-bottom:10px;" />

                <h4>${item.name}</h4>
                <p>${item.description}</p>
                <p><strong>₹${item.price}</strong></p>
            `;

            const editBtn = document.createElement("button");
            editBtn.textContent = "Edit";
            editBtn.style = "margin-right:10px;background:#ffc107;border:none;padding:5px 10px;border-radius:5px;cursor:pointer;";
            editBtn.addEventListener("click", () => {
                editingItemId = item.foodId;

                document.getElementById("itemName").value = item.name;
                document.getElementById("itemPrice").value = item.price;
                document.getElementById("itemDescription").value = item.description;

                document.getElementById("itemModal").style.display = "flex";
            });

            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "Delete";
            deleteBtn.style = "background:#dc3545;color:white;border:none;padding:5px 10px;border-radius:5px;cursor:pointer;";
            deleteBtn.addEventListener("click", () => {
                deleteMenuItem(item.foodId);
            });

            div.appendChild(editBtn);
            div.appendChild(deleteBtn);
            menuList.appendChild(div);
        });

    } catch (error) {
        console.error(error);
    }
}

/* =========================
   Save (Add + Update)
========================= */
async function saveMenuItem() {

    const name = document.getElementById("itemName").value;
    const price = document.getElementById("itemPrice").value;
    const description = document.getElementById("itemDescription").value;
    const imageFile = document.getElementById("itemImage").files[0];

    const formData = new FormData();
    formData.append("name", name);
    formData.append("description", description);
    formData.append("price", price);
    formData.append("availability", true);

    if (imageFile) {
        formData.append("image", imageFile);
    }

    let url;
    let method;

    if (editingItemId !== null) {
        url = `http://localhost:8080/food-items/${editingItemId}`;
        method = "PUT";
    } else {
        url = `http://localhost:8080/food-items/restaurant/${restaurantId}`;
        method = "POST";
    }

    try {
        const response = await fetch(url, {
            method: method,
            body: formData
        });

        if (response.ok) {
            alert(editingItemId !== null ? "Item updated!" : "Item added!");
            editingItemId = null;
            closeModal();
            loadMenu();
        } else {
            alert("Operation failed.");
        }

    } catch (error) {
        console.error(error);
    }
}

/* =========================
   Delete Item
========================= */
async function deleteMenuItem(foodId) {

    if (!confirm("Are you sure you want to delete this item?")) return;

    try {
        const response = await fetch(
            `http://localhost:8080/food-items/${foodId}`,
            { method: "DELETE" }
        );

        if (response.ok) {
            alert("Item deleted successfully!");
            loadMenu();
        } else {
            alert("Delete failed.");
        }

    } catch (error) {
        console.error(error);
    }
}
/* =========================
   Orders Section
========================= */
async function loadOrders() {
    try {
        const response = await fetch(`http://localhost:8080/orders/restaurant/${restaurantId}`);
        if (!response.ok) throw new Error("Failed to fetch orders");

        const orders = await response.json();
        renderOrders(orders);
    } catch (error) {
        console.error("Error loading orders:", error);
    }
}

function renderOrders(orders) {
    // Clear all tabs
    const tabs = ["received", "preparing", "past"];
    tabs.forEach(tab => {
        const el = document.getElementById(tab);
        if (el) el.innerHTML = "";
    });

    orders.forEach(order => {
        const orderId = order.orderId;

        const div = document.createElement("div");
        div.classList.add("order-item");
        div.style = `
            border:1px solid #ccc;
            padding:10px;
            margin-bottom:10px;
            border-radius:8px;
        `;

        // Customer name
        const customerName = order.customer?.username || "Unknown Customer";

        // Items
        const items = Array.isArray(order.orderItems) ? order.orderItems : [];
        let itemsHtml = "<ul>";
        items.forEach(item => {
            const itemName = item.foodName || `Item #${item.id}`; // now comes from backend
            const quantity = item.quantity || 0;
            const price = item.price || 0;
            const totalPrice = quantity * price;
            itemsHtml += `<li>${itemName} x ${quantity} = ₹${totalPrice}</li>`;
        });
        itemsHtml += "</ul>";

        // Grand total from backend
        const grandTotal = order.totalPrice || 0;

        div.innerHTML = `
            <strong>Order #${orderId}</strong> - ${customerName}<br>
            ${itemsHtml}
            <strong>Grand Total: ₹${grandTotal}</strong><br>
            <span id="status-${orderId}">Status: ${order.status || "UNKNOWN"}</span><br>
        `;

        // Buttons for active orders
        if (order.status === "RECEIVED") {
            const prepBtn = document.createElement("button");
            prepBtn.textContent = "Mark Preparing";
            prepBtn.style = "margin-right:5px;";
            prepBtn.onclick = () => updateOrderStatus(orderId, "PREPARING");

            const cancelBtn = document.createElement("button");
            cancelBtn.textContent = "Cancel";
            cancelBtn.style = "background:red;color:white;margin-left:5px;";
            cancelBtn.onclick = () => updateOrderStatus(orderId, "CANCELLED");

            div.appendChild(prepBtn);
            div.appendChild(cancelBtn);
        } else if (order.status === "PREPARING") {
            const deliverBtn = document.createElement("button");
            deliverBtn.textContent = "Mark Delivered";
            deliverBtn.style = "margin-right:5px;";
            deliverBtn.onclick = () => updateOrderStatus(orderId, "DELIVERED");

            div.appendChild(deliverBtn);
        }

        // Append to correct tab
        if (order.status === "RECEIVED") {
            document.getElementById("received").appendChild(div);
        } else if (order.status === "PREPARING") {
            document.getElementById("preparing").appendChild(div);
        } else if (order.status === "DELIVERED" || order.status === "CANCELLED") {
            document.getElementById("past").appendChild(div);
        }
    });
}

// Update order status
async function updateOrderStatus(orderId, newStatus) {
    try {
        const url = `http://localhost:8080/orders/${orderId}/status?status=${newStatus}`;
        console.log("Updating order:", url);

        const response = await fetch(url, { method: "PUT" });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to update order status: ${errorText}`);
        }

        // Reload orders after update
        loadOrders();
    } catch (error) {
        console.error(error);
        alert("Error updating order status. Check console for details.");
    }
}

// Switch tabs
function showOrderTab(tabId) {
    const tabs = ["received", "preparing", "delivered", "cancelled", "past"];
    tabs.forEach(id => {
        const el = document.getElementById(id);
        if (el) el.style.display = id === tabId ? "block" : "none";
    });
}

// Initial load
loadOrders();

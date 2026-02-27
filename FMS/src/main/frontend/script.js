/* =====================================================
   GLOBAL VARIABLES
===================================================== */
let allRestaurants = [];
let slideIndex = 0;


/* =====================================================
   SLIDESHOW
===================================================== */
function showSlides() {
    const slides = document.getElementsByClassName("slide");
    if (!slides.length) return;

    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }

    slides[slideIndex - 1].style.display = "block";
    setTimeout(showSlides, 3000);
}


/* =====================================================
   CART SYSTEM (GLOBAL)
===================================================== */
function getCart() {
    return JSON.parse(localStorage.getItem("cart")) || [];
}

function saveCart(cart) {
    localStorage.setItem("cart", JSON.stringify(cart));
}

function updateCartUI() {

    const cartContent = document.querySelector(".cart-content");
    if (!cartContent) return;

    const cart = getCart();

    if (cart.length === 0) {
        cartContent.innerHTML = "<p>Your cart is empty. Let's add some green!</p>";
        return;
    }

    cartContent.innerHTML = "";
    let total = 0;

    cart.forEach(item => {

        total += item.price * item.quantity;

        const div = document.createElement("div");
        div.style.marginBottom = "10px";

        div.innerHTML = `
            <strong>${item.name}</strong><br>
            ₹${item.price} × ${item.quantity}
        `;

        cartContent.appendChild(div);
    });

    const totalDiv = document.createElement("div");
    totalDiv.innerHTML = `<hr><strong>Total: ₹${total}</strong>`;
    cartContent.appendChild(totalDiv);
}

function addToCart(item) {

    let cart = getCart();

    const existingItem = cart.find(c => c.id === item.id);

    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({
            id: item.id,
            name: item.name,
            price: item.price,
            quantity: 1,
            image: item.foodImage || null
        });
    }

    saveCart(cart);
    updateCartUI();

    alert(item.name + " added to cart!");
}


/* =====================================================
   PROFILE + AUTH
===================================================== */
function initProfile() {

    const navUsername = document.getElementById("nav-username");
    const profileToggle = document.getElementById("profile-toggle");
    const profileDropdown = document.getElementById("profile-dropdown");
    const logoutBtn = document.getElementById("logout-btn");

    const username = localStorage.getItem("username");
    const customerId = localStorage.getItem("customerId");

    // If logged in
    if (username && customerId) {

        if (navUsername) {
            navUsername.textContent = username;
        }

        if (profileToggle) {
            profileToggle.addEventListener("click", function (e) {
                e.preventDefault();
                profileDropdown.classList.toggle("active");
            });
        }

    } else {

        // Not logged in
        if (navUsername) {
            navUsername.textContent = "Sign In";
        }

        if (profileToggle) {
            profileToggle.addEventListener("click", function (e) {
                e.preventDefault();
                window.location.href = "login.html";
            });
        }
    }

    // Logout
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            localStorage.removeItem("username");
            localStorage.removeItem("customerId");
            localStorage.removeItem("cart");
            window.location.href = "index.html";
        });
    }

    // Close dropdown when clicking outside
    document.addEventListener("click", function (e) {
        if (!e.target.closest(".profile-menu")) {
            if (profileDropdown) {
                profileDropdown.classList.remove("active");
            }
        }
    });
}


/* =====================================================
   RESTAURANTS
===================================================== */
function loadRestaurants() {

    const container = document.getElementById("restaurantList");
    if (!container) return;

    fetch("http://localhost:8080/restaurants/load-restaurant")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to load restaurants");
            }
            return response.json();
        })
        .then(data => {
            allRestaurants = data;
            displayRestaurants(allRestaurants);
        })
        .catch(error => {
            document.getElementById("message").innerText = error.message;
        });
}

function displayRestaurants(restaurants) {

    const container = document.getElementById("restaurantList");
    if (!container) return;

    container.innerHTML = "";

    restaurants.forEach(restaurant => {

        const card = document.createElement("div");
        card.className = "restaurant-card";

        const imageUrl = restaurant.imageUrl
            ? `http://localhost:8080/uploads/${restaurant.imageUrl}`
            : "https://via.placeholder.com/300x200?text=No+Image";

        card.innerHTML = `
            <img style="width:100%; height:250px; object-fit:cover;" 
                 src="${imageUrl}">
            <div style="padding:15px;">
                <h3>${restaurant.restaurantName}</h3>
                <p><strong>Cuisine:</strong> ${restaurant.cusine ?? "N/A"}</p>
                <p><strong>Address:</strong> ${restaurant.restaurantAddress ?? "N/A"}</p>
            </div>
        `;

        card.addEventListener("click", () => {
            localStorage.setItem("restaurantId", restaurant.rid);
            window.location.href = "menu.html";
        });

        container.appendChild(card);
    });
}


/* =====================================================
   SEARCH
===================================================== */
function initSearch() {

    const searchLink = document.querySelector('a[href="/search"]');
    const searchOverlay = document.getElementById("search-overlay");
    const closeSearch = document.getElementById("close-search");
    const searchInput = document.getElementById("search-input");

    if (!searchLink || !searchInput) return;

    searchLink.addEventListener("click", (e) => {
        e.preventDefault();
        searchOverlay.classList.add("active");
        searchInput.focus();
    });

    closeSearch.addEventListener("click", () => {
        searchOverlay.classList.remove("active");
    });

    searchInput.addEventListener("input", () => {

        const query = searchInput.value.trim().toLowerCase();

        if (!query) {
            displayRestaurants(allRestaurants);
            return;
        }

        const filtered = allRestaurants.filter(r =>
            r.restaurantName.toLowerCase().includes(query) ||
            (r.cusine && r.cusine.toLowerCase().includes(query)) ||
            (r.restaurantAddress && r.restaurantAddress.toLowerCase().includes(query))
        );

        displayRestaurants(filtered);
    });
}


/* =====================================================
   CART SIDEBAR TOGGLE
===================================================== */
function initCartToggle() {

    const cartLink = document.getElementById("cart-toggle") ||
                     document.querySelector('a[href="/cart"]');

    const cartSidebar = document.getElementById("cart-sidebar");
    const bodyOverlay = document.getElementById("body-overlay");
    const closeCart = document.getElementById("close-cart");

    if (!cartLink || !cartSidebar) return;

    cartLink.addEventListener("click", (e) => {
        e.preventDefault();
        cartSidebar.classList.add("active");
        bodyOverlay.classList.add("active");
        updateCartUI();
    });

    const hideCart = () => {
        cartSidebar.classList.remove("active");
        bodyOverlay.classList.remove("active");
    };

    if (closeCart) closeCart.addEventListener("click", hideCart);
    if (bodyOverlay) bodyOverlay.addEventListener("click", hideCart);
}


/* =====================================================
   INITIALIZE EVERYTHING
===================================================== */
document.addEventListener("DOMContentLoaded", function () {

    showSlides();
    initProfile();
    loadRestaurants();
    initSearch();
    initCartToggle();
    updateCartUI();
});
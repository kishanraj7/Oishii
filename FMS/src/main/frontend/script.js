let slideIndex = 0;
showSlides();

function showSlides() {
  let i;
  let slides = document.getElementsByClassName("slide");
  
  // Hide all slides
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  
  slideIndex++;
  
  // Reset to first slide if at the end
  if (slideIndex > slides.length) {
    slideIndex = 1;
  }    
  
  // Display the current slide
  slides[slideIndex - 1].style.display = "block";  
  
  // Change image every 3 seconds
  setTimeout(showSlides, 3000); 
}

document.addEventListener('DOMContentLoaded', () => {
    const searchLink = document.querySelector('a[href="/search"]');
    const cartLink = document.querySelector('a[href="/cart"]');
    const searchOverlay = document.getElementById('search-overlay');
    const cartSidebar = document.getElementById('cart-sidebar');
    const bodyOverlay = document.getElementById('body-overlay');
    const closeSearch = document.getElementById('close-search');
    const closeCart = document.getElementById('close-cart');

    // Prevent default navigation for demo
    const preventAction = (e) => e.preventDefault();

    // Search Toggle
    searchLink.addEventListener('click', (e) => {
        e.preventDefault();
        searchOverlay.classList.add('active');
        document.getElementById('search-input').focus();
    });

    closeSearch.addEventListener('click', () => {
        searchOverlay.classList.remove('active');
    });

    // Cart Toggle
    cartLink.addEventListener('click', (e) => {
        e.preventDefault();
        cartSidebar.classList.add('active');
        bodyOverlay.classList.add('active');
    });

    const hideCart = () => {
        cartSidebar.classList.remove('active');
        bodyOverlay.classList.remove('active');
    };

    closeCart.addEventListener('click', hideCart);
    bodyOverlay.addEventListener('click', hideCart);

    // Escape key to close everything
    document.addEventListener('keydown', (e) => {
        if (e.key === "Escape") {
            searchOverlay.classList.remove('active');
            hideCart();
        }
    });
    
});
document.addEventListener("DOMContentLoaded", function () {

    /* ---------------- PROFILE ---------------- */

    const navUsername = document.getElementById("nav-username");
    const profileToggle = document.getElementById("profile-toggle");
    const profileDropdown = document.getElementById("profile-dropdown");
    const logoutBtn = document.getElementById("logout-btn");

    const user = localStorage.getItem("leafboxUser");

    if (user && navUsername) {
        navUsername.textContent = user;
    }

    if (profileToggle) {
        profileToggle.addEventListener("click", function (e) {
            e.preventDefault();

            const currentUser = localStorage.getItem("leafboxUser");

            if (!currentUser) {
                window.location.href = "login.html";
            } else {
                profileDropdown.classList.toggle("active");
            }
        });
    }

    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            localStorage.removeItem("leafboxUser");
            window.location.reload();
        });
    }

});

/* Protect page */
const restaurantId = localStorage.getItem("restaurantId");
if(!restaurantId){
    window.location.href = "partner-login.html";
}

/* Switch Sections */
function showSection(sectionId){
    document.querySelectorAll(".section").forEach(sec => sec.classList.remove("active"));
    document.getElementById(sectionId).classList.add("active");
}
function openModal(){
    document.getElementById("itemModal").style.display = "flex";
}

function closeModal(){
    document.getElementById("itemModal").style.display = "none";
}

/* Logout */
function logout(){
    localStorage.removeItem("restaurantId");
    window.location.href = "partner-login.html";
}

/* Load Restaurant Profile */
async function loadProfile(){
    try{
        const response = await fetch(`http://localhost:8080/restaurants/find/${restaurantId}`);
        const data = await response.json();
        document.getElementById("restaurantId").innerText = data.rid;
        document.getElementById("restaurantName").innerText = data.restaurantName;
        document.getElementById("restaurantCuisine").innerText = data.cusine;
        document.getElementById("restaurantAddress").innerText = data.restaurantAddress;
        document.getElementById("restaurantPhone").innerText = data.restaurantPhoneNo;

        document.getElementById("profileImage").src = data.imageUrl ? `http://localhost:8080/uploads/${data.imageUrl}` : "https://via.placeholder.com/120?text=No+Image";

    }catch(error){
        console.error(error);
    }
}

loadProfile();
/* Enable Update Mode */
function enableEdit(){
    // Hide profile card
    document.querySelector(".profile-card").style.display = "none";

    // Show update form
    const updateForm = document.getElementById("updateForm");
    updateForm.style.display = "block";

    // Pre-fill current data
    document.getElementById("updateName").value = document.getElementById("restaurantName").innerText;
    document.getElementById("updateCuisine").value = document.getElementById("restaurantCuisine").innerText;
    document.getElementById("updateAddress").value = document.getElementById("restaurantAddress").innerText;
    document.getElementById("updatePhone").value = document.getElementById("restaurantPhone").innerText;
}

function cancelUpdate(){
    // Hide update form
    document.getElementById("updateForm").style.display = "none";
    // Show profile card
    document.querySelector(".profile-card").style.display = "block";
}

//Update Profile Function
async function submitUpdate(){
    const updatedData = new FormData();
    updatedData.append("restaurantName", document.getElementById("updateName").value);
    updatedData.append("cusine", document.getElementById("updateCuisine").value);
    updatedData.append("restaurantAddress", document.getElementById("updateAddress").value);
    updatedData.append("restaurantPhoneNo", document.getElementById("updatePhone").value);

    const imageFile = document.getElementById("updateImage").files[0];
    if(imageFile){
        updatedData.append("image", imageFile);
    }

    try{
        const response = await fetch(`http://localhost:8080/restaurants/${restaurantId}`, {
            method: "PUT",
            body: updatedData
        });

        if(response.ok){
            alert("Profile updated successfully!");
            // Reload profile to show updated data
            loadProfile();
            cancelUpdate();
        } else {
            alert("Failed to update profile.");
        }
    }catch(error){
        console.error(error);
        alert("An error occurred.");
    }
}

//Help Menu Functions
const helpData = {
    orders: [
        { title: "How to view incoming orders", content: "Go to the Orders tab to see new orders and prepare them..." },
        { title: "What to do if an order is canceled", content: "When an order is canceled by the customer, check your dashboard for details and contact support if needed..." }
    ],
    profile: [
        { title: "How to update restaurant profile", content: "Click on Profile > Update Profile, fill in the details and save changes..." },
        { title: "How to reset password", content: "Use the Forgot Password link on the login screen to receive OTP and reset your password." }
    ],
    payment: [
        { title: "Understanding payouts", content: "Payouts are processed weekly. Check the Payments section for details..." },
        { title: "Why am I not receiving payouts?", content: "Contact support with payout ID and transaction details to troubleshoot." }
    ],
    support: [
        { title: "Contact Partner Support", content: "Email us at partnersupport@leafbox.com or call +91‑XXXXXXXXXX for urgent help." }
    ]
};

// Load selected category
function loadHelpCategory(cat) {
    document.getElementById("helpDetail").style.display = "none";
    const itemsDiv = document.getElementById("helpItems");
    itemsDiv.innerHTML = "";
    helpData[cat].forEach((item, index) => {
        const btn = document.createElement("button");
        btn.className = "action";
        btn.style.marginBottom = "10px";
        btn.style.marginRight = "20px";
        btn.innerText = item.title;
        btn.onclick = () => showHelpDetail(cat, index);
        itemsDiv.appendChild(btn);
    });
}

// Show detailed help content
function showHelpDetail(cat, index) {
    const detailDiv = document.getElementById("helpDetail");
    detailDiv.style.display = "block";
    detailDiv.innerHTML = `
        <h3>${helpData[cat][index].title}</h3>
        <p>${helpData[cat][index].content}</p>
    `;
}

// Live search filter
function filterHelp() {
    const query = document.getElementById("helpSearch").value.toLowerCase();
    const itemsDiv = document.getElementById("helpItems");
    itemsDiv.innerHTML = "";
    Object.keys(helpData).forEach(cat => {
        helpData[cat]
            .filter(item => item.title.toLowerCase().includes(query))
            .forEach(item => {
                const btn = document.createElement("button");
                btn.className = "action";
                btn.style.marginBottom = "10px";
                btn.innerText = item.title;
                btn.onclick = () => {
                    document.getElementById("helpDetail").style.display = "block";
                    document.getElementById("helpDetail").innerHTML = `
                        <h3>${item.title}</h3><p>${item.content}</p>
                    `;
                };
                itemsDiv.appendChild(btn);
            });
    });
}
function openContactForm(){
    alert("Feature to submit a ticket coming soon!");
}

/* Add Menu Item */
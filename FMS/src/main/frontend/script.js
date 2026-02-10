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

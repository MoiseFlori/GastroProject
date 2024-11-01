// Selectăm elementele pentru dropdown
const authButton = document.querySelector('.auth-button-header');
const authOptions = document.querySelector('.auth-options');

// Funcție pentru a deschide și închide dropdown-ul
authButton.addEventListener('click', (event) => {
  event.stopPropagation(); // Prevenim propagarea evenimentului
  authOptions.style.display = authOptions.style.display === 'block' ? 'none' : 'block';
});

// Închidem dropdown-ul dacă dăm click în afara lui
document.addEventListener('click', () => {
  authOptions.style.display = 'none';
});

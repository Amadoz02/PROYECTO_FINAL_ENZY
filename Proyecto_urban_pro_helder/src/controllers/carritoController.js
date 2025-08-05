import { get, put, del } from '../utils/manejo_api.js';

export default async function carritoController() {
  const cartItemsContainer = document.getElementById('cart-grid');
  const cartSubtotal = document.getElementById('cart-subtotal');
  const cartTotal = document.getElementById('cart-total');
  const checkoutBtn = document.getElementById('checkout-btn');
  const idUsuario = sessionStorage.getItem('id_usuario');

  if (!idUsuario) {
    cartItemsContainer.innerHTML = `
      <div class="empty-cart">
        <p>Debes iniciar sesión para ver tu carrito.</p>
        <a href="#login" class="login-link">Iniciar Sesión</a>
      </div>
    `;
    return;
  }

  // Obtener los items del carrito desde la API
  async function getCartItems() {
      try {
          const idUsuario = sessionStorage.getItem('id_usuario');
          const response = await get(`detalles_carrito/usuario/${idUsuario}`); // Llama al nuevo endpoint
          return response || [];
      } catch (error) {
          console.error('Error al obtener items del carrito:', error);
          return [];
      }
  }
  console.log(await getCartItems());

  // Obtener información completa de cada producto
  async function getProductDetails(productId) {
    try {
      return await get(`productos/${productId}`);
    } catch (error) {
      console.error('Error al obtener detalles del producto:', error);
      return null;
    }
  }

  // Renderizar los items del carrito
  async function renderCartItems() {
    const cartItems = await getCartItems();
    
    if (cartItems.length === 0) {
      cartItemsContainer.innerHTML = `
        <div class="empty-cart">
          <p class="text">Tu carrito está vacío</p>
          <a href="#productos" class="continue-shopping">Continuar comprando</a>
        </div>
      `;
      updateCartTotals(0);
      return;
    }

    cartItemsContainer.innerHTML = ''; // Limpiar contenedor
    
    for (const item of cartItems) {
      const product = await getProductDetails(item.id_producto);
      if (!product) continue;

      const itemElement = document.createElement('div');
      itemElement.className = 'cart-item';
      
      itemElement.innerHTML = `
        <div class="cart-item__image">
          <img src="${product.imagenes?.[0]?.url_imagen || 'default-product.jpg'}" 
               alt="${product.nombre}">
        </div>
        <div class="cart-item__details">
          <h3 class="cart-item__title">${product.nombre}</h3>
          <p class="cart-item__category">${product.categoria}</p>
          <p class="cart-item__price">$${product.precio.toFixed(2)} c/u</p>
        </div>
        <div class="cart-item__quantity">
          <button class="quantity-btn decrement" 
                  data-detalle-id="${item.id_detalle}">-</button>
          <span class="quantity-value">${item.cantidad}</span>
          <button class="quantity-btn increment" 
                  data-detalle-id="${item.id_detalle}">+</button>
        </div>
        <div class="cart-item__total">
          $${(product.precio * item.cantidad).toFixed(2)}
        </div>
        <button class="cart-item__remove" 
                data-detalle-id="${item.id_detalle}">
          <i class="trash-icon" data-lucide="trash-2" data-detalle-id="${item.id_detalle}"></i>
        </button>
      `;
      
      cartItemsContainer.appendChild(itemElement);
    }

    // Actualizar totales
    updateCartTotals(cartItems);
    initializeCartEvents();
    if (window.lucide) lucide.createIcons();
  }

  // Actualizar totales del carrito
  function updateCartTotals(cartItems) {
    let subtotal = 0;
    
    if (cartItems.length > 0) {
      // Calcular subtotal sumando (precio * cantidad) de cada item
      subtotal = cartItems.reduce((sum, item) => {
        return sum + (item.producto?.precio || 0) * item.cantidad;
      }, 0);
    }

    // Podrías agregar cálculos de impuestos/envío aquí si es necesario
    const total = subtotal; // En este ejemplo, el total es igual al subtotal
    
    cartSubtotal.textContent = `$${subtotal.toFixed(2)}`;
    cartTotal.textContent = `$${total.toFixed(2)}`;
  }

  // Inicializar eventos de los botones
  function initializeCartEvents() {
    // Botones para incrementar/disminuir cantidad
    document.querySelectorAll('.quantity-btn').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const detalleId = e.target.dataset.detalleId;
        const isIncrement = e.target.classList.contains('increment');
        await updateCartItemQuantity(detalleId, isIncrement ? 1 : -1);
        await renderCartItems(); // Refrescar la vista
      });
    });

    // Botones para eliminar items
    document.querySelectorAll('.cart-item__remove').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const detalleId = e.target.closest('button').dataset.detalleId;
        await removeFromCart(detalleId);
        await renderCartItems(); // Refrescar la vista
      });
    });
  }

  // Actualizar cantidad de un item en el carrito
  async function updateCartItemQuantity(detalleId, change) {
    try {
      const currentItems = await getCartItems();
      const currentItem = currentItems.find(item => item.id_detalle_carrito == detalleId);
      
      if (!currentItem) return;

      const newQuantity = currentItem.cantidad + change;
      
      if (newQuantity < 1) {
        // Si la cantidad sería 0, eliminar el item
        await removeFromCart(detalleId);
      } else {
        // Actualizar la cantidad en el backend
        await put(`detalles_carrito/${detalleId}`, {
          cantidad: newQuantity
        });
      }
    } catch (error) {
      console.error('Error al actualizar cantidad:', error);
    }
  }

  // Eliminar item del carrito
  async function removeFromCart(detalleId) {
    try {
      await del(`detalles_carrito/${detalleId}`);
    } catch (error) {
      console.error('Error al eliminar del carrito:', error);
    }
  }

  // Evento para el botón de checkout
  checkoutBtn.addEventListener('click', () => {
    window.location.href = '#checkout';
  });

  // Cargar carrito al iniciar
  await renderCartItems();
}

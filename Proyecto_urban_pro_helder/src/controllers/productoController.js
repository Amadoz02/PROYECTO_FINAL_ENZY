export function initProductoController() {
  const productGrid = document.getElementById('product-grid');

  // Función para crear una tarjeta de producto con carrusel de imágenes
  function createProductCard(product) {
    const card = document.createElement('article');
    card.className = 'product-card';

    // Botón de favorito
    const favoriteBtn = document.createElement('button');
    favoriteBtn.className = 'product-card__favorite';
    favoriteBtn.setAttribute('aria-label', 'Agregar a favoritos');
    favoriteBtn.innerHTML = '<i data-lucide="heart" class="product-card__favorite-icon"></i>';
    favoriteBtn.dataset.productId = product.id_producto || '';
    console.log("id de usuario en sessionStorage: " + sessionStorage.getItem("id_usuario"));

    // Guardar id del producto
    favoriteBtn.addEventListener('click', async () => {
      const idProducto = favoriteBtn.dataset.productId;

      const idUsuario = sessionStorage.getItem("id_usuario");

      if (!idProducto || !idUsuario) {
        console.error('Faltan datos para guardar/eliminar favorito');
        return;
      }

      const favorito = {
        idUsuario: parseInt(idUsuario),
        idProducto: parseInt(idProducto)
      };

      console.log("Favorito a enviar:", favorito);
      // Alternar clase visual
      favoriteBtn.classList.toggle('product-card__favorite--active');

      // Verificar si el producto está marcado como favorito (después de toggle)
      const esFavorito = favoriteBtn.classList.contains('product-card__favorite--active');

      try {
        if (esFavorito) {
          // 🔺 AGREGAR FAVORITO
          const response = await fetch(`http://localhost:8080/helder/api/favoritos`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(favorito)
          });

          if (!response.ok) throw new Error('Error al agregar a favoritos');
          console.log('Producto agregado a favoritos:', idProducto);
        } else {
          // 🔻 ELIMINAR FAVORITO
           const response = await fetch(`http://localhost:8080/helder/api/favoritos/${idUsuario}/${idProducto}`, {
            method: 'DELETE'
          });

          if (!response.ok) throw new Error('Error al eliminar favorito');
          console.log('Producto eliminado de favoritos:', idProducto);
        }
      } catch (error) {
        console.error('Error al actualizar favoritos:', error);
      }
    });

    card.appendChild(favoriteBtn);

    // Contenedor del carrusel de imágenes
    const carouselContainer = document.createElement('div');
    carouselContainer.className = 'product-card__carousel';

    // Imagen visible
    const img = document.createElement('img');
    img.className = 'product-card__img';
    img.alt = product.nombre || 'Producto';

    // Índice de la imagen actual
    let currentImageIndex = 0;

    // Función para actualizar la imagen visible
    function updateImage() {
      if (product.imagenes && product.imagenes.length > 0) {
        img.src = product.imagenes[currentImageIndex].url_imagen;
        img.alt = product.imagenes[currentImageIndex].descripcion || product.nombre || 'Producto';
      } else {
        img.src = '../resources/img1.jpg'; // Imagen por defecto si no hay imágenes
        img.alt = product.nombre || 'Producto';
      }
    }

    updateImage();
    carouselContainer.appendChild(img);

    // Si hay más de una imagen, agregar botones para cambiar la imagen
    if (product.imagenes && product.imagenes.length > 1) {
      // Botón anterior
      const prevBtn = document.createElement('button');
      prevBtn.className = 'carousel-btn carousel-btn--prev';
      prevBtn.setAttribute('aria-label', 'Imagen anterior');
      prevBtn.textContent = '<';
      prevBtn.addEventListener('click', () => {
        currentImageIndex = (currentImageIndex - 1 + product.imagenes.length) % product.imagenes.length;
        updateImage();
      });
      carouselContainer.appendChild(prevBtn);

      // Botón siguiente
      const nextBtn = document.createElement('button');
      nextBtn.className = 'carousel-btn carousel-btn--next';
      nextBtn.setAttribute('aria-label', 'Imagen siguiente');
      nextBtn.textContent = '>';
      nextBtn.addEventListener('click', () => {
        currentImageIndex = (currentImageIndex + 1) % product.imagenes.length;
        updateImage();
      });
      carouselContainer.appendChild(nextBtn);
    }

    card.appendChild(carouselContainer);

    // Nombre del producto
    const title = document.createElement('h2');
    title.className = 'product-card__title';
    title.textContent = product.nombre || 'Sin nombre';
    card.appendChild(title);

    // Categoría
    const category = document.createElement('p');
    category.className = 'product-card__category';
    category.textContent = 'Categoría: ' + (product.categoria || 'N/A');
    card.appendChild(category);

    // Talla
    const size = document.createElement('p');
    size.className = 'product-card__size';
    size.textContent = 'Talla: ' + (product.talla || 'N/A');
    card.appendChild(size);

    // Género
    const gender = document.createElement('p');
    gender.className = 'product-card__gender';
    gender.textContent = 'Género: ' + (product.genero || 'N/A');
    card.appendChild(gender);

    // Precio
    const price = document.createElement('p');
    price.className = 'product-card__price';
    price.textContent = '$' + (product.precio || '0');
    card.appendChild(price);

    // Botón agregar
    const addButton = document.createElement('button');
    addButton.className = 'product-card__btn btn';
    addButton.textContent = 'Agregar';
    card.appendChild(addButton);

    return card;
  }

  // Función para cargar productos desde el backend
  async function loadProducts() {
    try {
      const response = await fetch('http://localhost:8080/helder/api/productos');
      if (!response.ok) {
        throw new Error('Error al obtener productos: ' + response.status);
      }
      const products = await response.json();

      // Limpiar el contenedor
      productGrid.innerHTML = '';

      // Crear y agregar tarjetas para cada producto
      products.forEach(product => {
        const card = createProductCard(product);
        productGrid.appendChild(card);
        if (window.lucide) {
          lucide.createIcons();// Renderizar iconos lucide
        }
      });
    } catch (error) {
      console.error('Error cargando productos:', error);
      productGrid.innerHTML = '<p>Error al cargar los productos.</p>';
    }
  }

  // Agregar evento click al icono favoritos en el header
  const headerFavoritesBtn = document.getElementById('favoritesBtn');
  if (headerFavoritesBtn) {
    headerFavoritesBtn.addEventListener('click', () => {
      // Aquí puedes agregar la lógica para mostrar la lista de favoritos o navegar a la página de favoritos
      console.log('Icono favoritos en header clickeado');
      // Por ejemplo, redirigir a la página de favoritos:
      window.location.href = '#favoritos';
    });
  }

  // Cargar productos al iniciar
  loadProducts();
}

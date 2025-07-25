const mainContent = document.getElementById("main-content");

// Rutas relativas partiendo desde public/
const vistasPublicas = {
  bienvenida: "/views/public/bienvenida.html",
  inicio: "/views/public/login.html",
  registro: "/views/public/register.html"
};

const vistasPrivadas = {
  1: {
    home: "views/admin/dashboard.html",
    usuarios: "views/admin/usuarios.html",
    productos: "views/admin/productos.html",
  },
  2: {
    home: "views/admin/dashboard.html",
    productos: "views/admin/productos.html",
  },
  3: {
    home: "views/cliente/home.html",
    productos: "views/cliente/productos.html",
    carrito: "views/cliente/carrito.html",
  },
};

export function cargarVistaDesdeHash() {
  const hash = location.hash.replace("#", "") || "bienvenida";
  const idRol = sessionStorage.getItem("id_rol");

  // Verificamos si es pública
  if (vistasPublicas[hash]) {
    fetch(vistasPublicas[hash])
      .then(res => {
        if (!res.ok) throw new Error("Vista pública no encontrada");
        return res.text();
      })
      .then(html => {
              console.log("HTML cargado:", html);
              mainContent.innerHTML = html;
            })
      .catch(err => {
        console.error(err);
        mainContent.innerHTML = "<h2>Error cargando vista pública.</h2>";
      });
    return;
  }

  // Si no tiene sesión, redirigir al login
  if (!idRol || !vistasPrivadas[idRol]) {
    location.hash = "inicio";
    return;
  }

  const vistaPrivada = vistasPrivadas[idRol][hash];

  if (!vistaPrivada) {
    mainContent.innerHTML = "<h2>Acceso denegado.</h2>";
    return;
  }

  fetch(vistaPrivada)
    .then(res => {
      if (!res.ok) throw new Error("Vista privada no encontrada");
      return res.text();
    })
    .then(html => {
      mainContent.innerHTML = html;
    })
    .catch(err => {
      console.error(err);
      mainContent.innerHTML = "<h2>Error cargando vista privada.</h2>";
    });
}

// Escuchar cambios en el hash
window.addEventListener("hashchange", cargarVistaDesdeHash);
window.addEventListener("DOMContentLoaded", cargarVistaDesdeHash);

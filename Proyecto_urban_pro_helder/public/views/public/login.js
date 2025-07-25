document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector(".form__group");

  form.addEventListener("submit", async (e) => {
    e.preventDefault(); // Evita recargar la página

    const correo = document.getElementById("email").value.trim();
    const contrasena = document.getElementById("password").value;

    const data = {
      correo: correo,
      contrasena: contrasena
    };

    try {
      const response = await fetch("http://localhost:8080/helder/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      });

      const result = await response.json();

      if (response.ok) {
        // Guardar tokens y rol en localStorage
      // Guardamos en sessionStorage lo necesario
      sessionStorage.setItem("token", data.token);
      sessionStorage.setItem("id_rol", data.id_rol);
      sessionStorage.setItem("id_usuario", data.id_usuario);


        // Redirigir al home o dashboard
        window.location.href = "#home"; // O como tengas definido
      } else {
        alert(result.error || "Error al iniciar sesión");
      }
    } catch (error) {
      console.error("Error en el login:", error);
      alert("No se pudo conectar con el servidor");
    }
  });
});

document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.querySelector('.menu-toggle');
    const navigation = document.querySelector('#main-navigation');

    if (toggle && navigation) {
        toggle.addEventListener('click', () => {
            const open = toggle.getAttribute('aria-expanded') === 'true';
            toggle.setAttribute('aria-expanded', String(!open));
            navigation.classList.toggle('open', !open);
        });
    }

    document.querySelectorAll('[data-current-year]').forEach((element) => {
        element.textContent = String(new Date().getFullYear());
    });

    document.querySelectorAll('.toast').forEach((toast) => {
        window.setTimeout(() => toast.remove(), 3200);
    });

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) entry.target.classList.add('visible');
        });
    }, { threshold: 0.08 });

    document.querySelectorAll('.reveal').forEach((element) => observer.observe(element));

    const loginForm = document.querySelector('[data-login-form]');
    const loginError = loginForm?.querySelector('[data-login-error]');
    if (loginForm && loginError) {
        const fields = [...loginForm.querySelectorAll('input[name="username"], input[name="password"]')];
        const clearLoginError = () => {
            loginError.remove();
            fields.forEach((field) => field.removeEventListener('input', clearLoginError));

            const url = new URL(window.location.href);
            if (url.searchParams.delete('error')) {
                window.history.replaceState(window.history.state, '', `${url.pathname}${url.search}${url.hash}`);
            }
        };
        fields.forEach((field) => field.addEventListener('input', clearLoginError));
    }

    const checkoutCart = document.querySelector('[data-checkout-cart]');
    if (checkoutCart) {
        const feedback = checkoutCart.querySelector('[data-cart-feedback]');
        const formatMoney = (value) => `S/ ${Number(value).toFixed(2)}`;

        const setLoading = (loading) => {
            checkoutCart.querySelectorAll('.checkout-cart-form button').forEach((button) => {
                if (loading) {
                    button.disabled = true;
                    return;
                }
                const item = button.closest('[data-cart-item]');
                const quantity = Number(item?.querySelector('[data-item-quantity]')?.textContent ?? 0);
                button.disabled = button.closest('[data-cart-operation="increase"]') !== null && quantity >= 20;
            });
        };

        const updateSummary = (cart) => {
            if (cart.items.length === 0) {
                window.location.assign('/menu');
                return;
            }

            checkoutCart.querySelectorAll('[data-cart-item]').forEach((element) => {
                const item = cart.items.find((candidate) => String(candidate.productoId) === element.dataset.productId);
                if (!item) {
                    element.remove();
                    return;
                }

                element.querySelector('[data-item-quantity]').textContent = String(item.cantidad);
                element.querySelector('[data-item-subtotal]').textContent = formatMoney(item.subtotal);
                element.querySelector('[data-cart-operation="decrease"] input[name="cantidad"]').value = String(item.cantidad - 1);
                element.querySelector('[data-cart-operation="increase"] input[name="cantidad"]').value = String(item.cantidad + 1);
            });

            checkoutCart.querySelector('[data-cart-total]').textContent = formatMoney(cart.total);
            const cartCount = document.querySelector('.cart-count');
            if (cartCount) cartCount.textContent = String(cart.cantidadTotal);
            feedback.textContent = 'Carrito actualizado';
            feedback.classList.remove('error');
        };

        checkoutCart.addEventListener('submit', async (event) => {
            const form = event.target.closest('.checkout-cart-form');
            if (!form) return;
            event.preventDefault();
            setLoading(true);

            try {
                const response = await fetch(form.action, {
                    method: 'POST',
                    body: new FormData(form),
                    headers: { 'Accept': 'application/json', 'X-Requested-With': 'XMLHttpRequest' }
                });
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                updateSummary(await response.json());
            } catch (error) {
                feedback.textContent = 'No pudimos actualizar el carrito. Inténtalo nuevamente.';
                feedback.classList.add('error');
            } finally {
                setLoading(false);
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const navToggle = document.querySelector('.nav-toggle');
    const navMenu = document.querySelector('.nav-menu');
    if (navToggle && navMenu) {
        navToggle.addEventListener('click', function () {
            const open = navMenu.classList.toggle('open');
            navToggle.setAttribute('aria-expanded', String(open));
        });
    }

    document.querySelectorAll('.dropdown-toggle').forEach(function (toggle) {
        toggle.addEventListener('click', function () {
            const group = toggle.closest('.nav-group');
            const open = !group.classList.contains('open');
            document.querySelectorAll('.nav-group.open').forEach(function (item) {
                item.classList.remove('open');
                const itemToggle = item.querySelector('.dropdown-toggle');
                if (itemToggle) itemToggle.setAttribute('aria-expanded', 'false');
            });
            group.classList.toggle('open', open);
            toggle.setAttribute('aria-expanded', String(open));
        });
    });
});

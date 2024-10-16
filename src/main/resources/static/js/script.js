function confirmLogout(event) {
    if (!confirm("Are you sure you want to logout?")) {
        event.preventDefault();  // Prevent logout if user clicks "Cancel"
    }
}

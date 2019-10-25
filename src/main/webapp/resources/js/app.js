function handleLoginRequest(xhr, status, args) {
	if (args.validationFailed) {
		PF('dlg').show();
	} else {
		PF('dlg').hide();
	}
}

jQuery(function($) {
	ajax_url = 'https://demo.wpschoolpress.com/wp-admin/admin-ajax.php';
	date_format = 'mm/dd/yy';
	$('.content-wrapper').on('click', function() {
		$('.control-sidebar').removeClass('control-sidebar-open');
	});
	$('body').addClass('wpschoolpress');
	$('html').removeClass('wp-toolbar');
});

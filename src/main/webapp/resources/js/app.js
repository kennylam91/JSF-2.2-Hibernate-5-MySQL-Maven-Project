function handleLoginRequest(xhr, status, args) {
	if (args.validationFailed) {
		PF('dlg').show();
	} else {
		PF('dlg').hide();
	}
}
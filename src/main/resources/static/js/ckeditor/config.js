CKEDITOR.editorConfig = function( config ) {
	config.toolbarGroups = [
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others', groups: [ 'Youtube', 'CodeSnippet' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'ckeditor_wiris_formulaEditor' },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.extraPlugins = 'youtube, ckeditor_wiris, codesnippet, sharedspace';

	config.removeButtons = 'Underline,Subscript,Superscript,Strike,Blockquote,About,Cut,Copy,Paste,PasteText,PasteFromWord,Anchor,Styles';
	config.language = 'en';
	//config.uiColor = '#E8d84A';
	config.height = 100;
	config.toolbarCanCollapse = true;
};



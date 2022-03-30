/**
 * Confirmador de exclusão de contato @ Dev Josiel Costa
 */

	function confirmar(idcon) {
		let resposta = confirm("Confirma a exclusão deste contato ?")
		if (resposta === true) {
			//alert(idcon) teste
			//encaminhar para o Servlet
			window.location.href = "delete?idcon" + idcon // redirecionando para o delete e o servelet trata a requisição
		}
			
	}
	
	
	

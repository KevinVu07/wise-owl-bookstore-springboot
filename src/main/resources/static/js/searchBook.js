const searchBook = () => {
	const searchbox = document.getElementById("search_book").value.toLowerCase();
	const book = document.querySelectorAll(".book_card");

	book.forEach(book => {
		const isVisible = book.innerText.toLowerCase().includes(searchbox);
		book.classList.toggle("d-none", !isVisible)
		}) 
}
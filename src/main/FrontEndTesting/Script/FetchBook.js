const bookList = document.getElementById('book-list');
bookList.innerHTML = ''; // Nettoie le contenu avant d'ajouter
const bookidSelect = document.getElementById('bookid_select');
const ul = document.createElement('ul');
bookidSelect.innerHTML=''; // Clear existing options


async function fetchOneBook(id) {
    try {
        const response = await fetch('http://localhost:8080/api/books/book/' + id);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const book = await response.json();
        const books=[book]; // Wrap single book in an array
        //console.log(Array.from(books));
        displayBooks(books);
    } catch (error) {
        console.error('Error fetching books:', error);
    }
}

async function fetchBooks() {
    try {
        const response = await fetch('http://localhost:8080/api/books');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const books = await response.json();
        displayBooks(books);
    } catch (error) {
        console.error('Error fetching books:', error);
    }
}

function displayBooks(books) {

    bookList.innerHTML = ''; // Clear previous list
    ul.innerHTML = ''; // Clear previous list items
    if (books.length === 0) {
        bookList.textContent = 'No books found.';
        return;
    }

    books.forEach(book => {
        const spanId= document.createElement('span');
        const spanAuth= document.createElement('span');
        spanId.textContent = ` ID: ${book.id}`;
        const li = document.createElement('li');
        li.appendChild(spanId);
        spanAuth.textContent = ` Author: ${book.authorName+' '+book.authorAge +' years old'}`;
        bookidSelect.innerHTML += `<option value="${book.id}">${book.title}</option>`;
        li.innerHTML  += `<br>${book.title} — ${book.pages} pages<br>`;
        li.appendChild(spanAuth);
        li.appendChild(document.createElement("hr"));
        ul.appendChild(li);

    });

    bookList.appendChild(ul);
}


const fetchButton = document.getElementById('load-books');
fetchButton.addEventListener('click', fetchBooks);
bookidSelect.addEventListener('change', (event) => {
    const selectedId = event.target.value;
    console.log('Selected book ID:', selectedId);
    fetchOneBook(selectedId);
});


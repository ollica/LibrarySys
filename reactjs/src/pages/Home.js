import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

export default function Home() {
  const [books, setBooks] = useState([]);
  const { id } = useParams();

  useEffect(() => {
    loadBooks();
  }, {});

  const loadBooks = async () => {
    const result = await axios.get('http://localhost:8080/api/items/books');
    setBooks(result.data);
  };

  const deleteBook = async (id) => {
    const result = await axios.delete(
      `http://localhost:8080/api/items/books/${id}`
    );
    loadBooks();
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">序号</th>
              <th scope="col">书名</th>
              <th scope="col">价格</th>
              <th scope="col">出版日期</th>
              <th scope="col">操作</th>
            </tr>
          </thead>
          <tbody>
            {books.map((book, index) => (
              <tr>
                <th scope="row" key={index}>
                  {index + 1}
                </th>
                <td>{book.name}</td>
                <td>{book.price}</td>
                <td>{book.pd}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewBook/${book.id}`}
                  >
                    查看
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/editBook/${book.id}`}
                  >
                    修改
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteBook(book.id)}
                  >
                    删除
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

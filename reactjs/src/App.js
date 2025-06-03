import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import NavBar from './components/Navbar';
import React from 'react';
import Home from './pages/Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AddBook from './pages/AddBook';
import EditBook from './pages/EditBook';
import ViewBook from './pages/ViewBook';

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/addBook" element={<AddBook />} />
          <Route exact path="/editBook/:id" element={<EditBook />} />
          <Route exact path="/viewBook/:id" element={<ViewBook />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

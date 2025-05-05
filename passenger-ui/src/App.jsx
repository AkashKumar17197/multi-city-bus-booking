import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/header/Header';
import Home from './pages/Home';
import PNRStatus from './pages/PNRStatus';
import CancelTicket from './pages/CancelTicket';
import FoodOrder from './pages/FoodOrder';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/pnr-status" element={<PNRStatus />} />
        <Route path="/cancel-ticket" element={<CancelTicket />} />
        <Route path="/food-order" element={<FoodOrder />} />
      </Routes>
    </Router>
  );
}

export default App;

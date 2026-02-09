import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/header/Header";
import Home from "./pages/Home";
import PNRStatus from "./pages/PNRStatus";
import CancelTicket from "./pages/CancelTicket";
import FoodOrder from "./pages/FoodOrder";
import Search from "./pages/Search";
import Booking from "./pages/Booking";
import Payment from "./pages/Payment";

import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

function App() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/pnr-status" element={<PNRStatus />} />
          <Route path="/cancel-ticket" element={<CancelTicket />} />
          <Route path="/food-order" element={<FoodOrder />} />
          <Route path="/search" element={<Search />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/payment" element={<Payment />} />
        </Routes>
      </Router>
    </LocalizationProvider>
  );
}

export default App;

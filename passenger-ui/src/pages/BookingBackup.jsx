import {
  Box,
  Container,
  Typography,
  Paper,
  Grid,
  TextField,
  Select,
  MenuItem,
  Button,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function Booking() {
  const location = useLocation();
  const navigate = useNavigate();
  const busData = location.state?.bus || {};

  const [boardingPoint, setBoardingPoint] = useState("");
  const [droppingPoint, setDroppingPoint] = useState("");

  const [selectedSeats, setSelectedSeats] = useState([]);
  const [passengers, setPassengers] = useState([]);

  // ✅ Contact details (ONE TIME)
  const [contact, setContact] = useState({
    email: "",
    phone: "",
  });

  /* ---------------- Seat Click ---------------- */

  const handleSeatClick = (seatNo, type, occupancy) => {
  if (occupancy !== "A") return;

  setSelectedSeats((prev) => {
    const exists = prev.find((s) => s.seatNo === seatNo);
    if (exists) {
      return prev.filter((s) => s.seatNo !== seatNo);
    }
    return [...prev, { seatNo, type }];
  });
};

  /* ---------------- Sync Passengers ---------------- */

  useEffect(() => {
  setPassengers(
    selectedSeats.map((seat) => ({
      seatNumber: seat.seatNo,
      seatType: seat.type,
      fullName: "",
      age: "",
      gender: "Male",
    }))
  );
}, [selectedSeats]);

  const handlePassengerChange = (idx, field, value) => {
    const updated = [...passengers];
    updated[idx][field] = value;
    setPassengers(updated);
  };

  /* ---------------- Contact Handler ---------------- */

  const handleContactChange = (field, value) => {
    setContact((prev) => ({ ...prev, [field]: value }));
  };

  /* ---------------- Seat Rendering ---------------- */

  const renderSeatBox = (type, seatNo, occupancy) => {
  if (type === "S") return <Box sx={{ width: 30, height: 30 }} />;

  const isSelected = selectedSeats.some(
    (s) => s.seatNo === seatNo
  );

  let bg = "#fff";
  let cursor = "default";

  if (occupancy === "A") {
    bg = isSelected ? "#4caf50" : "#fff";
    cursor = "pointer";
  }
  if (occupancy === "B") bg = "#aaa";
  if (occupancy === "L") bg = "#f8b3c8";
  if (type === "D") bg = "#ddd";

  const width = type === "SL" ? 60 : 30;

  const totalFare = selectedSeats.reduce((sum, seat) => {
  if (seat.type === "SL") {
    return sum + Number(busData.sleeperFare || 0);
  }
  if (seat.type === "ST") {
    return sum + Number(busData.seaterFare || 0);
  }
  return sum;
}, 0);

  return (
    <Box
      onClick={() => handleSeatClick(seatNo, type, occupancy)}
      sx={{
        width,
        height: 30,
        m: 0.5,
        border: "1px solid #444",
        backgroundColor: bg,
        fontSize: 11,
        textAlign: "center",
        lineHeight: "30px",
        cursor,
      }}
    >
      {seatNo !== "S" && seatNo !== "D" ? seatNo : ""}
    </Box>
  );
};

  const renderDeck = (layout, seats, occupied, label) => (
  <>
    <Typography fontWeight="bold" mt={2}>
      {label}
    </Typography>

    <Box sx={{ backgroundColor: "#ddd", p: 2 }}>
      {layout.map((row, rowIndex) => {
        const rowTypes = row.split(" ");
        const rowSeats = seats[rowIndex].split(" ");
        const rowOcc = occupied[rowIndex].split(" ");

        return (
          <Box key={`${label}-row-${rowIndex}`} display="flex">
            {rowTypes.map((type, i) => (
              <Box
                key={`${label}-${rowIndex}-${i}-${rowSeats[i]}`}
              >
                {renderSeatBox(type, rowSeats[i], rowOcc[i])}
              </Box>
            ))}
          </Box>
        );
      })}
    </Box>
  </>
);

/* ---------------- Fare Calculation ---------------- */

const totalFare = selectedSeats.reduce((sum, seat) => {
  if (seat.type === "SL") {
    return sum + Number(busData.sleeperFare || 0);
  }
  if (seat.type === "ST") {
    return sum + Number(busData.seaterFare || 0);
  }
  return sum;
}, 0);

  /* ---------------- Validation ---------------- */

  const canBook =
  selectedSeats.length > 0 &&
  passengers.every((p) => p.fullName && p.age && p.gender) &&
  contact.email &&
  contact.phone &&
  boardingPoint &&
  droppingPoint;

  /* ---------------- Book Handler ---------------- */

  const handleBooking = () => {
    const bookingPayload = {
  saId: busData.saId,
  busCode: busData.busCode,
  seats: selectedSeats,
  passengers,
  contact,
  boardingPoint,
  droppingPoint,
  totalFare,
};

    console.log("BOOKING PAYLOAD 👉", bookingPayload);
    alert("Booking initiated! Check console.");
    navigate("/payment", { state: bookingPayload });
    
  };

  /* ---------------- UI ---------------- */

  return (
    <Box sx={{ backgroundColor: "#e5ffe5", minHeight: "100vh", py: 4 }}>
      <Container maxWidth="lg">
        <Typography
          variant="h5"
          fontWeight="bold"
          sx={{ backgroundColor: "#009933", color: "#fff", p: 2 }}
        >
          Booking Review
        </Typography>

        {/* Bus Info */}
        <Paper sx={{ p: 3, my: 3 }}>
          <Typography fontWeight="bold">
            Bus Code: {busData.busCode}
          </Typography>
          <Typography>Bus Type: {busData.type}</Typography>
          <Typography>
            {busData.departure} – {busData.arrival}
          </Typography>
        </Paper>

        {/* Seat Selection */}
        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h6" fontWeight="bold">
            Seat Selection
          </Typography>

          {busData.lowerDeckLayout &&
            renderDeck(
              busData.lowerDeckLayout,
              busData.lowerDeckLayoutSeats,
              busData.lowerDeckLayoutSeatsOccupied,
              "Lower Deck"
            )}

          {busData.upperDeckLayout &&
            renderDeck(
              busData.upperDeckLayout,
              busData.upperDeckLayoutSeats,
              busData.upperDeckLayoutSeatsOccupied,
              "Upper Deck"
            )}

          <Typography mt={2}>
  Seats Selected:{" "}
  {selectedSeats.map((s) => s.seatNo).join(", ") || "None"}
</Typography>
        </Paper>

        {/* Contact Details (ONE TIME) */}
        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h6" fontWeight="bold">
            Contact Details
          </Typography>

          <Grid container spacing={2} mt={1}>
            <Grid item xs={6}>
              <TextField
                label="Email"
                fullWidth
                value={contact.email}
                onChange={(e) =>
                  handleContactChange("email", e.target.value)
                }
              />
            </Grid>

            <Grid item xs={6}>
              <TextField
                label="Phone Number"
                fullWidth
                value={contact.phone}
                onChange={(e) =>
                  handleContactChange("phone", e.target.value)
                }
              />
            </Grid>
          </Grid>
        </Paper>

        {/* Boarding & Dropping Points */}
<Paper sx={{ p: 3, mb: 3 }}>
  <Typography variant="h6" fontWeight="bold">
    Boarding & Dropping Points
  </Typography>

  <Grid container spacing={2} mt={1}>
    {/* Boarding */}
    <Grid item xs={6}>
      <Select
        fullWidth
        displayEmpty
        value={boardingPoint}
        onChange={(e) => setBoardingPoint(e.target.value)}
      >
        <MenuItem value="">Select Boarding Point</MenuItem>
        {(busData.boardingPoints || []).map((bp) => (
          <MenuItem key={bp.id} value={bp}>
            {bp.point} ({bp.time})
          </MenuItem>
        ))}
      </Select>
    </Grid>

    {/* Dropping */}
    <Grid item xs={6}>
      <Select
        fullWidth
        displayEmpty
        value={droppingPoint}
        onChange={(e) => setDroppingPoint(e.target.value)}
      >
        <MenuItem value="">Select Dropping Point</MenuItem>
        {(busData.droppingPoints || []).map((dp) => (
          <MenuItem key={dp.id} value={dp}>
            {dp.point} ({dp.time})
          </MenuItem>
        ))}
      </Select>
    </Grid>
  </Grid>
</Paper>

        {/* Passenger Details */}
        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h6" fontWeight="bold">
            Passenger Details
          </Typography>

          {passengers.map((pax, idx) => (
            <Box key={idx} sx={{ my: 2 }}>
              <Typography>
                Passenger {idx + 1} – Seat {pax.seatNumber}
              </Typography>

              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <TextField
                    label="Full Name"
                    fullWidth
                    value={pax.fullName}
                    onChange={(e) =>
                      handlePassengerChange(idx, "fullName", e.target.value)
                    }
                  />
                </Grid>

                <Grid item xs={3}>
                  <TextField
                    label="Age"
                    type="number"
                    fullWidth
                    value={pax.age}
                    onChange={(e) =>
                      handlePassengerChange(idx, "age", e.target.value)
                    }
                  />
                </Grid>

                <Grid item xs={3}>
                  <Select
                    fullWidth
                    value={pax.gender}
                    onChange={(e) =>
                      handlePassengerChange(idx, "gender", e.target.value)
                    }
                  >
                    <MenuItem value="Male">Male</MenuItem>
                    <MenuItem value="Female">Female</MenuItem>
                    <MenuItem value="Other">Other</MenuItem>
                  </Select>
                </Grid>
              </Grid>
            </Box>
          ))}
        </Paper>

        {/* Fare */}
        <Paper sx={{ p: 3 }}>
          <Typography fontWeight="bold">
            Total Fare: ₹{totalFare}
          </Typography>

          <Button
            fullWidth
            variant="contained"
            sx={{ mt: 2 }}
            disabled={!canBook}
            onClick={handleBooking}
          >
            Book Now
          </Button>
        </Paper>
      </Container>
    </Box>
  );
}

export default Booking;

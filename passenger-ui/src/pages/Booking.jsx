import {
  Box,
  Container,
  Typography,
  Paper,
  Grid,
  Divider,
  TextField,
  InputLabel,
  FormGroup,
  FormControlLabel,
  Checkbox,
  Select,
  MenuItem,
  Radio,
  RadioGroup,
  FormControl,
  FormLabel,
  Button,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function Booking() {
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [passengers, setPassengers] = useState([]);
  const [sharedInfo, setSharedInfo] = useState({
    email: "",
    phone: "",
    idType: "",
    idNumber: "",
  });
  //const busType = busData.type || "Seater";

  const location = useLocation();
  const busData = location.state?.bus || {};

  const handleSeatClick = (key) => {
    if (selectedSeats.includes(key)) {
      setSelectedSeats(selectedSeats.filter((s) => s !== key));
    } else {
      setSelectedSeats([...selectedSeats, key]);
    }
  };

  useEffect(() => {
    const updated = selectedSeats.map((seat, idx) => ({
      seatNumber: seat,
      fullName: "",
      age: "",
      gender: "Male",
    }));
    setPassengers(updated);
  }, [selectedSeats]);

  const handlePassengerChange = (index, field, value) => {
    const updated = [...passengers];
    updated[index][field] = value;
    setPassengers(updated);
  };

  const handleSharedInfoChange = (field, value) => {
    setSharedInfo({ ...sharedInfo, [field]: value });
  };

  const Legend = ({ color, label }) => (
    <Box display="flex" alignItems="center" gap={1}>
      <Box
        sx={{
          width: 20,
          height: 20,
          backgroundColor: color,
          border: "1px solid #000",
        }}
      />
      <Typography variant="caption">{label}</Typography>
    </Box>
  );

  const renderSeatBox = (code, key) => {
    const commonStyles = {
      m: 0.5,
      display: "inline-block",
      border: "1px solid #444",
      backgroundColor: selectedSeats.includes(key) ? "#4caf50" : "#fff",
      textAlign: "center",
      fontSize: 12,
      cursor: "pointer",
    };

    switch (code) {
      case "ST":
        return (
          <Box
            key={key}
            sx={{ ...commonStyles, width: 30, height: 30 }}
            onClick={() => handleSeatClick(key)}
          ></Box>
        );
      case "SL":
        return (
          <Box
            key={key}
            sx={{ ...commonStyles, width: 60, height: 30 }}
            onClick={() => handleSeatClick(key)}
          ></Box>
        );
      case "D":
        return (
          <Box key={key} sx={{ ...commonStyles, width: 30, height: 30 }}>
            🚞
          </Box>
        );
      case "S":
        return (
          <Box
            key={key}
            sx={{
              ...commonStyles,
              width: 30,
              height: 30,
              backgroundColor: "transparent",
              border: "none",
            }}
          />
        );
      default:
        return <Box key={key} />;
    }
  };

  const renderDeck = (layoutArray, label) => (
    <>
      <Typography fontWeight="bold" mt={2}>
        {label}
      </Typography>
      <Box sx={{ backgroundColor: "#ddd", p: 2 }}>
        {layoutArray.map((row, rowIndex) => (
          <Box key={rowIndex} display="flex">
            {row
              .split(" ")
              .map((seatCode, seatIndex) =>
                renderSeatBox(seatCode, `${label}-${rowIndex}-${seatIndex}`)
              )}
          </Box>
        ))}
      </Box>
    </>
  );

  return (
    <Box sx={{ backgroundColor: "#e5ffe5", minHeight: "100vh", py: 4 }}>
      <Container maxWidth="lg">
        <Typography
          variant="h5"
          fontWeight="bold"
          gutterBottom
          sx={{
            backgroundColor: "#009933",
            color: "white",
            p: 2,
            borderRadius: 1,
          }}
        >
          Booking Review
        </Typography>

        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Typography variant="h6" fontWeight="bold">
            Bus Details
          </Typography>
          <Divider sx={{ my: 2 }} />
          <Box>
            <Typography variant="subtitle1" fontWeight="Bold">
              Bus Code: {busData.busCode}
            </Typography>
            <Typography variant="subtitle2" mb={2}>
              Bus Type: {busData.type}
            </Typography>
            <Box sx={{ display: "flex", alignItems: "center", mb: 2 }}>
              <Typography>Chennai</Typography>
              <Box
                sx={{
                  flexGrow: 1,
                  mx: 2,
                  height: 4,
                  backgroundColor: "black",
                  borderRadius: 2,
                }}
              />
              <Typography>Bengaluru</Typography>
            </Box>
            <Typography variant="body2">
              {busData.departure} - {busData.arrival}
            </Typography>
            <Typography variant="body2" mb={2}>
              Via: {busData.route}
            </Typography>
            <Box
              sx={{ display: "flex", justifyContent: "space-between", mb: 2 }}
            >
              <Box>
                <Typography fontWeight="bold">Travel Details</Typography>
                <Typography variant="body2">
                  Travel Distance: {busData.distance} km
                </Typography>
                <Typography variant="body2">
                  Travel Duration: {busData.duration}
                </Typography>
                <Typography variant="body2">
                  Rest Stops: {busData.restStops}
                </Typography>
              </Box>
              <Box>
                <Typography fontWeight="bold">Bus Fare</Typography>
                <Typography variant="body2">
                  Ticket Fee: {busData.fare}
                </Typography>
                <Typography variant="body2">Online Fee: ₹15</Typography>
                <Typography variant="body2">Filling Charges: ₹15</Typography>
                <Typography variant="body2">Swachh Bharat: ₹1</Typography>
                <Typography variant="body2">GST: ₹2</Typography>
                <Typography fontWeight="bold" mt={1}>
                  Total Fee: {Number(busData.fare) + (15 + 15 + 1 + 10)}
                </Typography>
              </Box>
            </Box>

            <Typography fontWeight="bold" gutterBottom>
              Amenities
            </Typography>
            <Box sx={{ display: "flex", gap: 2, flexWrap: "wrap", mb: 2 }}>
              {busData.amenities.map((item) => (
                <Box
                  key={item}
                  sx={{ display: "flex", alignItems: "center", gap: 1 }}
                >
                  <span>✅</span>
                  <Typography variant="body2">{item}</Typography>
                </Box>
              ))}
            </Box>

            <Typography fontWeight="bold" gutterBottom>
              Pictures
            </Typography>
            <Box sx={{ display: "flex", gap: 2, overflowX: "auto" }}>
              {busData.busPhotos.map((src, idx) => (
                <Box
                  key={idx}
                  component="img"
                  src={src}
                  alt={`Bus Interior ${idx + 1}`}
                  sx={{
                    borderRadius: 1,
                    width: 120,
                    height: 80,
                    objectFit: "cover",
                  }}
                />
              ))}
            </Box>
          </Box>
        </Paper>

        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Typography variant="h6" fontWeight="bold">
            Seat Selection
          </Typography>
          <Divider sx={{ my: 2 }} />

          <Typography variant="body2" gutterBottom>
            Bus Type: {busData.type}
          </Typography>
          <Typography variant="body2" gutterBottom>
            Seats Available ({busData.availableSeats}) | Window Seats -{" "}
            {busData.windowSeats}
          </Typography>

          {/* Layout Rendering */}
          {busData.lowerDeckLayout?.length > 0 &&
            renderDeck(busData.lowerDeckLayout, "Lower Deck")}
          {busData.upperDeckLayout?.length > 0 &&
            renderDeck(busData.upperDeckLayout, "Upper Deck")}

          {/* Legend */}
          <Box mt={2} display="flex" gap={2} flexWrap="wrap">
            <Legend color="#fff" label="Available" />
            <Legend color="#aaa" label="Full" />
            <Legend color="#f0f" label="Seat for Ladies" />
            <Legend color="#00f" label="Seat for Gents" />
            <Legend color="#fbb" label="Seat for Ladies Full" />
            <Legend color="#bbf" label="Seat for Gents Full" />
          </Box>

          <Box textAlign="center" sx={{ mt: 3 }}>
            <Typography variant="body2" gutterBottom>
              Seat Selected: {selectedSeats.length}
            </Typography>
            <Box
              component="img"
              src={`/seatselect${selectedSeats.length}.jpg`}
              sx={{ width: 100, height: 100 }}
            />
          </Box>
        </Paper>

        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Typography variant="h6" fontWeight="bold">
            Boarding/Dropping Points
          </Typography>
          <Divider sx={{ my: 2 }} />
          <Box sx={{ my: 4, p: 3, bgcolor: "#d1ffd6", borderRadius: 2 }}>
            <Grid container spacing={4}>
              {/* Boarding Points */}
              <Grid item xs={12} md={6}>
                <FormControl component="fieldset">
                  <FormLabel component="legend">Boarding Points</FormLabel>
                  <RadioGroup
                    name="boarding"
                    defaultValue={busData.boardingPoints?.[0]?.point || ""}
                  >
                    {busData.boardingPoints?.map((bp) => (
                      <FormControlLabel
                        key={bp.id}
                        value={bp.point}
                        control={<Radio />}
                        label={`${bp.point} (${bp.time})`}
                      />
                    ))}
                  </RadioGroup>
                </FormControl>
              </Grid>

              {/* Dropping Points */}
              <Grid item xs={12} md={6}>
                <FormControl component="fieldset">
                  <FormLabel component="legend">Dropping Points</FormLabel>
                  <RadioGroup
                    name="dropping"
                    defaultValue={busData.droppingPoints?.[0]?.point || ""}
                  >
                    {busData.droppingPoints?.map((dp) => (
                      <FormControlLabel
                        key={dp.id}
                        value={dp.point}
                        control={<Radio />}
                        label={`${dp.point} (${dp.time})`}
                      />
                    ))}
                  </RadioGroup>
                </FormControl>
              </Grid>
            </Grid>
          </Box>
        </Paper>

        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Typography variant="h6" fontWeight="bold">
            Passenger Details
          </Typography>
          <Divider sx={{ my: 2 }} />

          {passengers.map((pax, index) => (
            <Box
              key={index}
              sx={{ p: 3, mb: 2, bgcolor: "#f1f1f1", borderRadius: 2 }}
            >
              <Typography variant="subtitle1" gutterBottom>
                Passenger {index + 1} (Seat #{pax.seatNumber})
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Full Name"
                    variant="outlined"
                    fullWidth
                    required
                    value={pax.fullName}
                    onChange={(e) =>
                      handlePassengerChange(index, "fullName", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={12} sm={3}>
                  <TextField
                    label="Age"
                    variant="outlined"
                    fullWidth
                    required
                    type="number"
                    value={pax.age}
                    onChange={(e) =>
                      handlePassengerChange(index, "age", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={12} sm={3}>
                  <FormControl component="fieldset">
                    <FormLabel component="legend">Gender</FormLabel>
                    <RadioGroup
                      row
                      value={pax.gender}
                      onChange={(e) =>
                        handlePassengerChange(index, "gender", e.target.value)
                      }
                    >
                      <FormControlLabel
                        value="Male"
                        control={<Radio />}
                        label="Male"
                      />
                      <FormControlLabel
                        value="Female"
                        control={<Radio />}
                        label="Female"
                      />
                      <FormControlLabel
                        value="Other"
                        control={<Radio />}
                        label="Other"
                      />
                    </RadioGroup>
                  </FormControl>
                </Grid>
              </Grid>
            </Box>
          ))}

          {/* Shared Info for all passengers */}
          <Box sx={{ p: 3, mt: 4 }} component={Paper}>
            <Typography variant="h6" gutterBottom>
              Contact Details
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  label="Email Address"
                  variant="outlined"
                  fullWidth
                  required
                  type="email"
                  value={sharedInfo.email}
                  onChange={(e) =>
                    handleSharedInfoChange("email", e.target.value)
                  }
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  label="Mobile Number"
                  variant="outlined"
                  fullWidth
                  required
                  type="tel"
                  value={sharedInfo.phone}
                  onChange={(e) =>
                    handleSharedInfoChange("phone", e.target.value)
                  }
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <FormControl fullWidth>
                  <InputLabel>ID Proof</InputLabel>
                  <Select
                    value={sharedInfo.idType}
                    onChange={(e) =>
                      handleSharedInfoChange("idType", e.target.value)
                    }
                  >
                    <MenuItem value="Aadhaar">Aadhaar</MenuItem>
                    <MenuItem value="PAN">PAN</MenuItem>
                    <MenuItem value="Passport">Passport</MenuItem>
                    <MenuItem value="Driving License">Driving License</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  label="ID Proof Number"
                  variant="outlined"
                  fullWidth
                  value={sharedInfo.idNumber}
                  onChange={(e) =>
                    handleSharedInfoChange("idNumber", e.target.value)
                  }
                />
              </Grid>
            </Grid>
          </Box>
        </Paper>

        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Typography variant="h6" fontWeight="bold">
            Verification
          </Typography>
          <Divider sx={{ my: 2 }} />
          <Box sx={{ p: 3, mt: 4 }} component={Paper}>
            <Typography variant="h6" gutterBottom>
              Verification & Booking
            </Typography>

            {/* Captcha Mock */}
            <Box sx={{ display: "flex", alignItems: "center", gap: 2, mt: 2 }}>
              <Box
                sx={{
                  backgroundColor: "#e0e0e0",
                  p: 1,
                  fontWeight: "bold",
                  fontSize: 18,
                  letterSpacing: 2,
                  borderRadius: 1,
                  minWidth: 100,
                  textAlign: "center",
                }}
              >
                9K4XZ
              </Box>
              <TextField label="Enter Captcha" variant="outlined" required />
            </Box>

            {/* Terms and Booking Button */}
            <FormControlLabel
              control={<Checkbox required />}
              label="I agree to the terms and conditions"
              sx={{ mt: 2 }}
            />

            <Button
              variant="contained"
              color="primary"
              fullWidth
              sx={{ mt: 2, py: 1.5, fontWeight: "bold", fontSize: 16 }}
            >
              Book Now
            </Button>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
}

export default Booking;

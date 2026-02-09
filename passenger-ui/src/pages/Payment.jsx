import {
  Box,
  Container,
  Typography,
  Paper,
  Select,
  MenuItem,
  TextField,
  Button,
} from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";

function Payment() {
  const location = useLocation();
  const navigate = useNavigate();
  const booking = location.state || {};

  const [gateway, setGateway] = useState("");
  const [password, setPassword] = useState("");
  const [status, setStatus] = useState(null); // success | failed

  const handlePayment = () => {
  if (password === "1234") {
    setStatus("success");

    // redirect to home after 2 seconds
    setTimeout(() => {
      navigate("/");
    }, 5000);

  } else {
    setStatus("failed");

    // redirect to search page after 2 seconds
    setTimeout(() => {
      navigate("/search");
    }, 5000);
  }
};

  return (
    <Box sx={{ backgroundColor: "#f5f5f5", minHeight: "100vh", py: 4 }}>
      <Container maxWidth="sm">
        <Paper sx={{ p: 3 }}>
          <Typography variant="h6" fontWeight="bold">
            Payment
          </Typography>

          <Typography mt={1}>
            Amount to Pay: ₹{booking.totalFare}
          </Typography>

          {/* Payment Gateway */}
          <Select
            fullWidth
            sx={{ mt: 2 }}
            value={gateway}
            onChange={(e) => setGateway(e.target.value)}
            displayEmpty
          >
            <MenuItem value="">Select Payment Gateway</MenuItem>
            <MenuItem value="Razorpay">Razorpay (UPI, Cards)</MenuItem>
            <MenuItem value="PayU">PayU (Cards, EMI)</MenuItem>
            <MenuItem value="Cashfree">Cashfree (UPI)</MenuItem>
            <MenuItem value="Paytm">Paytm PG</MenuItem>
            <MenuItem value="PhonePe">PhonePe PG</MenuItem>
            <MenuItem value="CCAvenue">CCAvenue</MenuItem>
            <MenuItem value="BillDesk">BillDesk</MenuItem>
          </Select>

          {/* Password */}
          <TextField
            label="Payment Password"
            type="password"
            fullWidth
            sx={{ mt: 2 }}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <Button
            fullWidth
            variant="contained"
            sx={{ mt: 3 }}
            disabled={!gateway || !password}
            onClick={handlePayment}
          >
            Pay Now
          </Button>

          {/* Result */}
          {status === "success" && (
            <Typography color="green" fontWeight="bold" mt={2}>
              ✅ Payment Successful
            </Typography>
          )}

          {status === "failed" && (
            <Typography color="red" fontWeight="bold" mt={2}>
              ❌ Payment Failed
            </Typography>
          )}
        </Paper>
      </Container>
    </Box>
  );
}

export default Payment;

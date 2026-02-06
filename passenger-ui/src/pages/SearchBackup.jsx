import { useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import {
  Box,
  Button,
  Checkbox,
  Container,
  Divider,
  FormControlLabel,
  FormGroup,
  Grid,
  Paper,
  Slider,
  TextField,
  Typography,
  Autocomplete,
} from "@mui/material";

import dayjs from "dayjs"; // Ensure this is installed: npm install dayjs
import { DatePicker } from "@mui/x-date-pickers";
import { cityOptions } from "../data/cities";
import { busResult } from "../data/buses";

function Search() {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);

  const fromParam = params.get("from");
  const toParam = params.get("to");
  const dateParam = params.get("date");

  const [from, setFrom] = useState(null);
  const [to, setTo] = useState(null);
  const [date, setDate] = useState(null);

  /*const cityOptions = [
    { id: 1, city: "Chennai" },
  ];*/

  const filterCities = (options, { inputValue }) => {
    if (inputValue.length < 1) return [];
    return options.filter((option) =>
      option.city.toLowerCase().includes(inputValue.toLowerCase())
    );
  };

  const timingOptions = [
    "Morning 5 to 11",
    "Afternoon 12 to 3",
    "Evening 4 to 7",
    "Night 8 to 11",
    "Midnight",
  ];
  const busTypes = ["Seater", "Seater Cum Sleeper", "Sleeper", "Double Decker"];

  /*const busResult = [
    {
      operator: "SRM Travels",
      departure: "05:00",
      arrival: "11:00",
      fare: "535",
      busCode: "CNBBNG0500",
      route: "Via VLR, KGI, HSR",
      restStops: 2,
      availableSeats: 30,
      windowSeats: 10,
      type: "Double Decker",
    },];*/

  const modifySearch = () => {
    if (from && to && date) {
      const formattedDate = dayjs(date).format("YYYY-MM-DD");
      alert(`${from?.city} to ${to?.city} on ${formattedDate}`);
      navigate(
        `/search?from=${encodeURIComponent(from.city)}&to=${encodeURIComponent(
          to.city
        )}&date=${formattedDate}&flag=${true}`
      );
    } else {
      alert("Please fill all fields");
    }
  };

  useEffect(() => {
    console.log(location.search);
    if (fromParam) {
      const fromCity = cityOptions.find(
        (option) => option.city.toLowerCase() === fromParam.toLowerCase()
      );
      4;

      if (fromCity) setFrom(fromCity);
    }

    if (toParam) {
      const toCity = cityOptions.find(
        (option) => option.city.toLowerCase() === toParam.toLowerCase()
      );
      if (toCity) setTo(toCity);
    }

    if (dateParam) {
      const parsedDate = dayjs(dateParam);
      if (parsedDate.isValid()) setDate(parsedDate);
    }
  }, [fromParam, toParam, dateParam]);

  return (
    <Box sx={{ backgroundColor: "#f2f2f2", minHeight: "100vh", py: 4 }}>
      <Container sx={{ width: "100vw" }}>
        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={4}>
              <Autocomplete
                options={cityOptions}
                value={from}
                getOptionLabel={(option) => option.city}
                filterOptions={filterCities}
                onChange={(e, newValue) => setFrom(newValue)}
                popupIcon={null} // <-- This removes the dropdown arrow icon
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Origination"
                    variant="outlined"
                    sx={{ width: "200px" }}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <Autocomplete
                options={cityOptions}
                value={to}
                getOptionLabel={(option) => option.city}
                filterOptions={filterCities}
                onChange={(e, newValue) => setTo(newValue)}
                popupIcon={null} // <-- This removes the dropdown arrow icon
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Destination"
                    variant="outlined"
                    sx={{ width: "200px" }}
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <DatePicker
                label="Journey Date"
                value={date}
                onChange={(newValue) => setDate(newValue)}
                slotProps={{
                  textField: {
                    fullWidth: true,
                    size: "medium",
                  },
                }}
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <Button
                variant="contained"
                color="success"
                fullWidth
                sx={{
                  height: "56px", // match MUI default input height
                  borderRadius: 1,
                  fontWeight: "bold",
                  boxShadow: 2,
                  width: "200px",
                }}
                onClick={modifySearch}
              >
                MODIFY SEARCH
              </Button>
            </Grid>
          </Grid>
        </Paper>
        <Grid container spacing={3}>
          <Grid item xs={12} md={4}>
            <Paper elevation={3} sx={{ p: 3, backgroundColor: "#c1f0c1" }}>
              <Typography variant="h6" gutterBottom>
                Filters
              </Typography>
              <Typography variant="subtitle1" sx={{ mt: 2 }}>
                Timings
              </Typography>
              <FormGroup>
                {timingOptions.map((label) => (
                  <FormControlLabel
                    key={label}
                    control={<Checkbox defaultChecked />}
                    label={label}
                  />
                ))}
              </FormGroup>
              <Divider sx={{ my: 2 }} />
              <Typography variant="subtitle1">Fare</Typography>
              <Slider defaultValue={30} min={0} max={100} />
              <Divider sx={{ my: 2 }} />
              <FormGroup>
                {busTypes.map((label) => (
                  <FormControlLabel
                    key={label}
                    control={<Checkbox />}
                    label={label}
                  />
                ))}
              </FormGroup>
            </Paper>
          </Grid>

          <Grid item xs={12} md={8}>
            <Paper elevation={3} sx={{ p: 4, mb: 3, width: "100%" }}>
              <Typography sx={{ mt: 2 }}>
                Available Buses from {fromParam} to {toParam} on {dateParam}
              </Typography>
            </Paper>

            {busResult.map((bus, index) => (
              <Paper
                key={index}
                elevation={2}
                sx={{
                  p: 2,
                  mb: 3,
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  width: "100%",
                }}
              >
                <Box>
                  <Typography variant="subtitle2" fontWeight="bold">
                    {bus.busCode || "CNBBNG0600"}
                  </Typography>
                  <Typography sx={{ mt: 1 }}>
                    {bus.departure} - {bus.arrival}
                  </Typography>
                </Box>

                <Box>
                  <Typography>{bus.type}</Typography>
                  <Typography fontSize={14} color="text.secondary">
                    {bus.route}
                  </Typography>
                  <Typography fontSize={14} color="text.secondary">
                    Rest Stops: {bus.restStops}
                  </Typography>
                </Box>

                <Box textAlign="center">
                  <Typography>Seats Available</Typography>
                  <Typography fontWeight="bold">
                    {bus.availableSeats || 30}
                  </Typography>
                  <Typography fontSize={12} color="text.secondary">
                    Window Seats: {bus.windowSeats || 10}
                  </Typography>
                </Box>

                <Box>
                  <Typography fontWeight="bold" fontSize={16}>
                    Rs. {bus.fare}
                  </Typography>
                </Box>

                <Button
                  variant="contained"
                  color="success"
                  onClick={() => navigate("/booking", { state: { bus } })}
                >
                  Book Now
                </Button>
              </Paper>
            ))}
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}

export default Search;

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
import { DatePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import axios from "axios";

function Search() {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);

  // ⬇️ THESE ARE CITY IDs (string)
  const fromParam = params.get("from");
  const toParam = params.get("to");
  const dateParam = params.get("date");

  const [cityOptions, setCityOptions] = useState([]);

  const [from, setFrom] = useState(null); // city object
  const [to, setTo] = useState(null);     // city object
  const [date, setDate] = useState(null);

  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(false);

  /* ------------------ FILTER HELPERS ------------------ */

  const filterCities = (options, { inputValue }) => {
    if (!inputValue) return [];
    return options.filter((option) =>
      (option.city + option.code)
        .toLowerCase()
        .includes(inputValue.toLowerCase())
    );
  };

  const timingOptions = [
    "Morning 5 to 11",
    "Afternoon 12 to 3",
    "Evening 4 to 7",
    "Night 8 to 11",
    "Midnight",
  ];

  const busTypes = [
    "Seater",
    "Seater Cum Sleeper",
    "Sleeper",
    "Double Decker",
  ];

  /* ------------------ FETCH CITIES ------------------ */

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/cities")
      .then((res) => {
        const mapped = res.data
          .filter((c) => c.status === "LIVE")
          .map((c) => ({
            id: c.cityId,
            city: c.cityName,
            code: c.cityCode,
            parentCityId: c.parentCityId,
          }));

        setCityOptions(mapped);
      })
      .catch((err) => console.error("City fetch error", err));
  }, []);

  /* ------------------ URL → FORM SYNC (CITY ID BASED) ------------------ */

  useEffect(() => {
    if (!cityOptions.length) return;

    if (fromParam) {
      const city = cityOptions.find(
        (c) => c.id === Number(fromParam)
      );
      if (city) setFrom(city);
    }

    if (toParam) {
      const city = cityOptions.find(
        (c) => c.id === Number(toParam)
      );
      if (city) setTo(city);
    }

    if (dateParam) {
      const parsed = dayjs(dateParam);
      if (parsed.isValid()) setDate(parsed);
    }
  }, [fromParam, toParam, dateParam, cityOptions]);

  /* ------------------ MODIFY SEARCH (KEEP CITY ID) ------------------ */

  const modifySearch = () => {
    if (from && to && date) {
      navigate(
        `/search?from=${from.id}&to=${to.id}&date=${dayjs(date).format(
          "YYYY-MM-DD"
        )}`
      );
    } else {
      alert("Please fill all fields");
    }
  };

  /* ------------------ FETCH BUSES ------------------ */

  useEffect(() => {
    if (!fromParam || !toParam || !dateParam) return;

    setLoading(true);

    axios
      .get("http://localhost:8090/api/bus-search/search", {
        params: {
          fromCityId: fromParam,
          toCityId: toParam,
          date: dateParam,
        },
      })
      .then((res) => setBuses(res.data))
      .catch((err) => console.error("Bus search error:", err))
      .finally(() => setLoading(false));
  }, [fromParam, toParam, dateParam]);

  /* ------------------ UI ------------------ */

  return (
    <Box sx={{ backgroundColor: "#f2f2f2", minHeight: "100vh", py: 4 }}>
      <Container>
        {/* SEARCH BAR */}
        <Paper elevation={3} sx={{ p: 3, mb: 4 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={4}>
              <Autocomplete
                options={cityOptions}
                value={from}
                getOptionLabel={(o) => o.city + " - " + o.code}
                filterOptions={filterCities}
                onChange={(e, v) => setFrom(v)}
                renderInput={(p) => (
                  <TextField {...p} label="Origination" />
                )}
              />
            </Grid>

            <Grid item xs={12} sm={4}>
              <Autocomplete
                options={cityOptions}
                value={to}
                getOptionLabel={(o) => o.city + " - " + o.code}
                filterOptions={filterCities}
                onChange={(e, v) => setTo(v)}
                renderInput={(p) => (
                  <TextField {...p} label="Destination" />
                )}
              />
            </Grid>

            <Grid item xs={12} sm={4}>
              <DatePicker
                label="Journey Date"
                value={date}
                onChange={setDate}
                slotProps={{ textField: { fullWidth: true } }}
              />
            </Grid>

            <Grid item xs={12}>
              <Button
                variant="contained"
                color="success"
                onClick={modifySearch}
              >
                MODIFY SEARCH
              </Button>
            </Grid>
          </Grid>
        </Paper>

        <Grid container spacing={3}>
          {/* FILTERS */}
          <Grid item xs={12} md={4}>
            <Paper sx={{ p: 3 }}>
              <Typography variant="h6">Filters</Typography>

              <FormGroup>
                {timingOptions.map((t) => (
                  <FormControlLabel
                    key={t}
                    control={<Checkbox />}
                    label={t}
                  />
                ))}
              </FormGroup>

              <Divider sx={{ my: 2 }} />

              <Typography>Fare</Typography>
              <Slider min={0} max={3000} />

              <Divider sx={{ my: 2 }} />

              <FormGroup>
                {busTypes.map((t) => (
                  <FormControlLabel
                    key={t}
                    control={<Checkbox />}
                    label={t}
                  />
                ))}
              </FormGroup>
            </Paper>
          </Grid>

          {/* BUS LIST */}
          <Grid item xs={12} md={8}>
            <Paper sx={{ p: 3, mb: 3 }}>
              <Typography>
                Available Buses from {from?.city} to {to?.city} on {dateParam}
              </Typography>
            </Paper>

            {loading && <Typography>Loading buses...</Typography>}

            {buses.map((bus, index) => (
              <Paper key={index} sx={{ p: 2, mb: 3 }}>
                <Grid container spacing={2} alignItems="center">
                  <Grid item xs={3}>
                    <Typography fontWeight="bold">{bus.busCode}</Typography>
                    <Typography>
                      {bus.departure} - {bus.arrival}
                    </Typography>
                  </Grid>

                  <Grid item xs={3}>
                    <Typography>{bus.type}</Typography>
                    <Typography fontSize={13}>
                      Duration: {bus.duration}
                    </Typography>
                  </Grid>

                  <Grid item xs={3}>
                    <Typography>
                      Seats: <b>{bus.availableSeats}</b>
                    </Typography>
                    <Typography fontSize={13}>
                      Window: {bus.windowSeats}
                    </Typography>
                  </Grid>

                  <Grid item xs={3}>
                    <Button
                      variant="contained"
                      color="success"
                      fullWidth
                      onClick={() =>
                        navigate("/booking", { state: { bus } })
                      }
                    >
                      Book Now
                    </Button>
                  </Grid>
                </Grid>
              </Paper>
            ))}
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}

export default Search;
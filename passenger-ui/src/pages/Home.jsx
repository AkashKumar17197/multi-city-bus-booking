import {
  Box,
  Typography,
  TextField,
  Button,
  Container,
  Grid,
  Paper,
  Autocomplete,
  Card, 
  CardContent, 
  CardMedia, 
  CardActions,
  CardActionArea,
  Modal
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { useState } from 'react';
//import { useNavigate } from 'react-router-dom';
import dayjs from 'dayjs'; // Ensure this is installed: npm install dayjs

function Home() {
  const [from, setFrom] = useState(null);
  const [to, setTo] = useState(null);
  const [date, setDate] = useState(null);
  
  //const navigate = useNavigate();

  const cityOptions = [
    { id: 1, city: 'Chennai' },
    { id: 2, city: 'Bengaluru' },
    { id: 3, city: 'Pondicherry' },
    { id: 4, city: 'Madurai' },
    { id: 5, city: 'Coimbatore' },
    { id: 6, city: 'Nagercoil' },
    { id: 7, city: 'Trichy' },
    { id: 8, city: 'Ernakulam' },
    { id: 9, city: 'Trivandrum' },
    { id: 10, city: 'Mysore' },
    { id: 11, city: 'Mangaluru' },
    { id: 12, city: 'Hubbali' },
    { id: 13, city: 'Tirupati' },
    { id: 14, city: 'Kurnool' },
    { id: 15, city: 'Vijayawada' },
    { id: 16, city: 'Hyderabad' },
    { id: 17, city: 'Vishakapatnam' },
    { id: 18, city: 'Goa' },
  ];

  const popularRoutes = [
    'Chennai → Bangalore',
    'Hyderabad → Mumbai',
    'Delhi → Jaipur',
    'Kolkata → Patna',
    'Ahmedabad → Surat',
    'Pune → Goa',
    'Chennai → Bangalore',
    'Hyderabad → Mumbai',
    'Delhi → Jaipur',
    'Kolkata → Patna',
    'Ahmedabad → Surat',
    'Pune → Goa',
  ];


  const handleSearch = () => {
    if (from && to && date) {
      const formattedDate = dayjs(date).format('YYYY-MM-DD');
      //navigate(`/buses?from=${from}&to=${to}&date=${formattedDate}`);
      alert(`${from?.city} to ${to?.city} on ${formattedDate}`);
    } else {
      alert('Please fill all fields');
    }
  };

  const filterCities = (options, { inputValue }) => {
    if (inputValue.length < 1) return [];
    return options.filter((option) =>
      option.city.toLowerCase().includes(inputValue.toLowerCase())
    );
  };

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: '#a62020',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const newsData = [
  {
    title: "Senior Citizen Travellers 30% Discount",
    code: "BD0132",
    image: "/senior citizen pitcure.jpg",
    description: "Special 30% discount for senior citizens on all bookings. Travel with comfort and savings.",
  },
  {
    title: "Group Travelling with 15% Discount",
    code: "BD1265",
    image: "/group traveling booking.jpg",
    description: "Plan your trip with friends or family and enjoy a 15% group discount.",
  },
  {
    title: "Tirumala Tirupati Darshan Available",
    code: "BD1135",
    image: "/Tirupati Darshan Booking.jpg",
    description: "Book now for a divine Tirupati Darshan experience. Limited slots available.",
  },
];

const [openModalIndex, setOpenModalIndex] = useState(null);

  const handleOpenModal = (index) => {
    setOpenModalIndex(index);
  };

  const handleCloseModal = () => {
    setOpenModalIndex(null);
  };

  return (
    <Box>
      {/* Bus Background Section */}
      <Box
        sx={{
          backgroundImage: `url('/budlo bus pitcure.jpg')`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          py: 6,
        }}
      >
        <Container>
        <Grid
  container
  spacing={2}
  alignItems="center"
  component={Paper}
  sx={{
    p: 2,
    borderRadius: 2,
    boxShadow: 3,
    maxWidth: '100%',
  }}
>
  <Grid item xs={12} sm={6} md={3}>
    <Autocomplete
  options={cityOptions}
  value={from}
  getOptionLabel={(option) => option.city}
  filterOptions={filterCities}
  onChange={(e, newValue) => setFrom(newValue)}
  popupIcon={null} // <-- This removes the dropdown arrow icon
  renderInput={(params) => (
    <TextField {...params} label="Origination" variant="outlined" />
  )}
/>
  </Grid>

  <Grid item xs={12} sm={6} md={3}>
   <Autocomplete
                options={cityOptions}
                value={to}
                getOptionLabel={(option) => option.city}
                filterOptions={filterCities}
                onChange={(e, newValue) => setTo(newValue)}
                popupIcon={null} // <-- This removes the dropdown arrow icon
                renderInput={(params) => (
                  <TextField {...params} label="Destination" variant="outlined" />
                )}
              />
  </Grid>

  <Grid item xs={12} sm={6} md={3}>
    <DatePicker
      label="Journey Date"
      value={date}
      onChange={(newValue) => setDate(newValue)}
      slotProps={{
        textField: {
          fullWidth: true,
          size: 'medium',
        },
      }}
    />
  </Grid>

  <Grid item xs={12} sm={6} md={3}>
    <Button
      variant="contained"
      color="success"
      fullWidth
      sx={{
        height: '56px', // match MUI default input height
        borderRadius: 1,
        fontWeight: 'bold',
        boxShadow: 2,
      }}
      onClick={handleSearch}
    >
      SEARCH BUSES
    </Button>
  </Grid>
</Grid>

        </Container>
      </Box>

      {/* News and Offers */}
      <Box sx={{ backgroundColor: '#d0f5d6', p: 2, mt: 2 }}>
      <Typography variant="h6" color="green" sx={{ mb: 2 }}>
        News and Offers
      </Typography>

      <Grid container spacing={2}>
        {newsData.map((item, index) => (
          <Grid item xs={12} md={4} key={index}>
            <Card>
              <CardMedia
                component="img"
                height="140"
                image={item.image}
                alt={item.title}
              />
              <CardContent>
                <Typography variant="subtitle1" fontWeight="bold">
                  {item.title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Coupon Code: {item.code}
                </Typography>
              </CardContent>
              <CardActions>
                <Button
                  onClick={() => handleOpenModal(index)}
                  fullWidth
                  sx={{
                    backgroundColor: 'green',
                    color: 'white',
                    '&:hover': { backgroundColor: 'darkgreen' },
                  }}
                >
                  Read More
                </Button>
              </CardActions>
            </Card>

            {/* Modal for each card */}
            <Modal
              open={openModalIndex === index}
              onClose={handleCloseModal}
              aria-labelledby={`modal-title-${index}`}
              aria-describedby={`modal-description-${index}`}
            >
              <Box sx={style}>
                <Typography id={`modal-title-${index}`} variant="h6" component="h2">
                  {item.title}
                </Typography>
                <Typography id={`modal-description-${index}`} sx={{ mt: 2 }}>
                  {item.description}
                </Typography>
              </Box>
            </Modal>
          </Grid>
        ))}
      </Grid>
    </Box>


      {/* Popular Routes */}
      <Box sx={{ backgroundColor: '#d0f5d6', p: 2, mt: 2 }}>
  <Typography variant="h6" color="green" fontWeight="bold" mb={2}>
    Popular Routes
  </Typography>
  <Grid container spacing={2}>
    {popularRoutes.map((route, index) => (
      <Grid item xs={12} sm={6} md={4} key={index}>
        <Paper
          elevation={3}
          sx={{
            p: 2,
            textAlign: 'center',
            backgroundColor: '#fff',
            cursor: 'pointer',
            borderRadius: 2,
            fontWeight: 500
          }}
          onClick={() => console.log(`Route clicked: ${route}`)}
        >
          {route}
        </Paper>
      </Grid>
    ))}
  </Grid>
</Box>
    </Box>
  );
}

export default Home;

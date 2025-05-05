import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <AppBar position="static">
      <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="h6" component="div">
          Multi-City Bus Booking
        </Typography>

        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button color="inherit" component={Link} to="/">Home</Button>
          <Button color="inherit" component={Link} to="/pnr-status">PNR Status</Button>
          <Button color="inherit" component={Link} to="/cancel-ticket">Cancel Ticket</Button>
          <Button color="inherit" component={Link} to="/food-order">Food Order</Button>
        </Box>

        <Box sx={{ display: 'flex', gap: 1 }}>
          <Button color="inherit">Language</Button>
          <Button color="inherit">Color</Button>
          <Button color="inherit">Text Size</Button>
          <Button color="inherit">Operator Login</Button>
          <Button color="inherit">Guest Login</Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Header;

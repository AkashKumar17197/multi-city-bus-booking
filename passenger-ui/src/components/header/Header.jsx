import { AppBar, Toolbar, Typography, Button, Box, Select, MenuItem, IconButton } from '@mui/material';
import PersonOutlineIcon from '@mui/icons-material/PersonOutline';
import { Link } from 'react-router-dom';

function Header() {

  const colorThemes = [{color: 'red', message: 'Red Color Theme Changed'},
                       {color: 'blue', message: 'Blue Color Theme Changed'},
                       {color: 'green', message: 'Green Color Theme Changed'},
                       {color: 'yellow', message: 'Yellow Color Theme Changed'},
                      ];

  return (
    <Box>
    <Box sx={{ backgroundColor: '#d0f5d6', padding: '10px'}}>
      
      <Box display="flex" justifyContent="space-between" alignItems="center">
        <Box display="flex" alignItems="center" gap={2}>
          <Select defaultValue="English" size="small" sx={{ backgroundColor: 'white'}}>
            <MenuItem value="English">English</MenuItem>
            <MenuItem value="Hindi">Hindi</MenuItem>
            <MenuItem value="Tamil">Tamil</MenuItem>
            <MenuItem value="Telugu">Telugu</MenuItem>
            <MenuItem value="Malayalam">Malayalam</MenuItem>
            <MenuItem value="Kannada">Kannada</MenuItem>
          </Select>
        </Box>
        <Box display="flex" gap={1}>
        {colorThemes.map((theme, index) => (
          <Box key={index}
               sx={{ width: 20, height: 20,borderRadius: '50%', backgroundColor: theme.color, cursor: 'pointer'}}
               onClick={() => alert(theme.message)}
               />
        ))}
        </Box>
        <Box display="flex" gap={1}>
          <Button variant="outlined" size='small' onClick={() => alert('Text Size Increased')}>A+</Button>
          <Button variant="outlined" size='small' onClick={() => alert('Text Size Decreased')}>A-</Button>
        </Box>
        
        <Typography color="black">Welcome User</Typography>
      </Box>
    </Box>


      
      <Box display="flex" justifyContent="space-between" alignItems="center" gap={2} sx={{ backgroundColor: 'lightgreen', padding: '10px'}}>
        <Box textAlign="center" mt={1}>
          <Typography variant="h4" sx={{ fontWeight: 'bold'}} color="black">budlo</Typography>
          <Typography variant="subtitle1" color="black">travel anytime anywhere</Typography>
        </Box>
        <Box display="flex" sx={{ padding: '10px'}}>
          <Box display="flex" flexDirection="column" alignItems="center">
            <IconButton><PersonOutlineIcon/></IconButton>
            <Typography variant="caption" color="black">Guest login</Typography>
          </Box>
          <Box display="flex" flexDirection="column" alignItems="center">
            <IconButton><PersonOutlineIcon/></IconButton>
            <Typography variant="caption" color="black">Operator login</Typography>
          </Box>
        </Box>
      </Box>

      <AppBar position="static" sx={{ backgroundColor: 'green' }}>
        <Toolbar variant="dense">
          <Button color="inherit" component={Link} to="/">Home</Button>
          <Button color="inherit" component={Link} to="/pnr-status">PNR Status</Button>
          <Button color="inherit" component={Link} to="/cancel-ticket">Cancel Ticket</Button>
          <Button color="inherit" component={Link} to="/food-order">Food Order</Button>
        </Toolbar>
      </AppBar>
</Box>
    
  );
}

export default Header;

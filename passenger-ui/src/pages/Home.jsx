import { Typography, Container, Paper, TextField, Button, Grid, Card, CardContent, Link } from "@mui/material";
import { useState } from "react";

function Home()
{
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');
    const [date, setDate] = useState('');

    const handleSearch = () => {
        console.log("Searched Clicked");
        if (from && to && date)
        {
            console.log(`{from : ${from}, to: ${to}, date=${date}}`);
            //navigate(`/buses?from=${from}&to=${to}&date=${date}`);
        }
        else
        {
            alert('Please fill all fields');
        }
    };

    return (
        <div style={{backgroundImage: `url('/budlo bus pitcure.jpg')`,
                     backgroundSize: "cover",
                     backgroundPosition: "center",
                     minHeight: "100vh",
                     paddingTop: "50px"
        }}>
            <Container maxWidth="sm">
                <Paper elevation={6} sx={{ padding: 4, backgroundColor: "rgba(255,255,255,0.9)"}}>
                    <Typography variant="h4" gutterBottom align="center">
                        Search Your Bus
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField fullWidth label="From" value={from} onChange={(e) => setFrom(e.target.value)} />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField fullWidth label="To" value={to} onChange={(e) => setTo(e.target.value)} />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField fullWidth type="date" label="Journey Date" InputLabelProps={{ shrink: true }} value={date} onChange={(e) => setDate(e.target.value)}/>
                        </Grid>
                        <Grid item xs={12}>
                            <Button variant="contained" color="primary" fullWidth onClick={handleSearch}>
                              Search Buses
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </Container>
            <Container sx={{mt: 6}}>
                <Typography variant="h5" gutterBottom>
                    News and Offers
                </Typography>
                <Grid container spacing={3}>
                    {[
                        {
                         title: 'Summer Travel Discounts',
                         desc: 'Get up to 30% off on AC sleeper buses this summer!',
                         link: '#'
                        },
                        {
                            title: 'New Routes Added',
                            desc: 'We \' added 10+ new routes across South India.',
                            link: '#'
                        },
                        {
                            title: 'Festive Combo Offers',
                            desc: 'Book a bus + food combo and save rs.100 !',
                            link: '#'
                        }
                    ].map((item, index) => (
                        <Grid item xs={12} md={4} key={index}>
                            <Card elevation={3}>
                                <CardContent>
                                    <Typography variant="h6">{item.title}</Typography>
                                    <Typography variant="body2" sx={{mt: 1}}>{item.desc}</Typography>
                                    <Link href={item.link} sx={{mt: 2, display: 'block'}}>
                                    Read more →
                                    </Link>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Container>
            <Container sx={{mt: 6, mb: 6}}>
                <Typography variant="h5" gutterBottom>
                    Popular Routes
                </Typography>
                <Grid container spacing={3}>
                    {[
                        'Chennai - Bengaluru',
                        'Hyderabad - Vijayawada',
                        'Mumbai - Pune',
                        'Delhi - Jaipur',
                        'Bengaluru - Coimbatore',
                        'Ahmedabad - Surat'
                    ].map((route, index) => (
                        <Grid item xs={12} sm={6} md={4} key={index}>
                            <Paper 
                              elevation={3} 
                              sx={{ 
                                p: 2, 
                                textAlign: 'center', 
                                cursor: 'pointer', 
                                transition: '0.3s', 
                                '&:hover': {backgroundColor: '#f5f5f5'}
                            }}>
                                <Typography variant="body1">{route}</Typography>
                            </Paper>
                        </Grid>
                    ))}
                </Grid>
            </Container>
        </div>
    );
}

export default Home;
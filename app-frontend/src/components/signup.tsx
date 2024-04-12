import React, { useState } from "react";
import {Slider, Grid, Paper, TextField, Typography, Link as MuiLink, Button, Stack, useTheme, useMediaQuery } from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import { KeyboardReturn } from "@mui/icons-material";
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import { useNavigate } from 'react-router-dom';


const marks = [
    {
      value: 0,
      label: '0',
    },
    {
      value: 20,
      label: '20',
    },
    {
      value: 37,
      label: '40',
    },
    {
      value: 100,
      label: '60',
    },
  ];
  
  function valuetext(value: number) {
    return `${value}°C`;
  }

function Signup(){
    const navigate = useNavigate();
    const[username,setUsername]=useState<string>('');
      const[password,setPassword]=useState<string>('');
      const[firstName,setFirstName]=useState<string>('');
      const[lastName,setLastname]=useState<string>('');
      const[isTrainer,setisTrainer]=useState(false);

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>, checked: boolean) => {
      setisTrainer(checked); // Directly use the `checked` boolean provided by MUI
    };


      type SignupFormState = {
        username: string;
        password: string;
        firstName : string ;
        lastName:String ;
        isTrainer: boolean ;
      };

    
    
      const handleSignup = async(event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault() ;
        const url = 'http://localhost:8081/accounts/signup';
        const data: SignupFormState = { username, password,firstName,lastName,isTrainer };
    
        try {
          const response = await fetch(url, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
          });
    
          if (response.ok) {
            const result = await response.json();
            console.log('Login Successful:', result);
            if(result.isTrainer==true){
              navigate('/TrainerView');
            }
            else{
              navigate('/ClientView');
            }
          } else {
            throw new Error('Failed to login');
          }
        } catch (error) {
          console.error('Login Error:', error);
        }
      }

    return(
        <Grid container component="main" sx={{ height: '100vh', overflow: 'hidden' }}>
             <Grid item md={7} sx={{
            backgroundImage: 'url(samuel-girven-VJ2s0c20qCo-unsplash.jpg)', 
            backgroundRepeat: 'no-repeat',
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            position: 'relative',
          }}>
            <Box sx={{
              position: 'absolute',
              bottom: 30,
              left: 30,
              color: 'white',
              backgroundColor: 'rgba(0, 0, 0, 0.5)',
              p: 3,
              borderRadius: '4px'
            }}>
              <Typography variant="h5">Welcome</Typography>
              <Typography variant="body2">Please sign up to continue</Typography>
            </Box>
          </Grid>
          <Grid item xs={12} md={5} component={Paper} elevation={6} square>
        <Stack sx={{ my: 8, mx: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
          <Box
            sx={{
            display: 'flex',
            alignItems: 'center',
            '& > :not(style)': { m: 1 },
            }}>
                  <TextField
              margin="normal"
              required
              fullWidth
              id="First Name"
              label="First Name"
              name="First Name"
              autoComplete="First Name"
              autoFocus
              variant="outlined"
              value={firstName}
              onChange={e => setFirstName(e.target.value)} 

            />

            <TextField
              margin="normal"
              required
              fullWidth
              id="Last Name"
              label="Last Name"
              name="Last Name"
              autoComplete="Last Name"
              autoFocus
              variant="outlined"
              value={lastName}
              onChange={e => setLastname(e.target.value)} 

            />
          </Box>

          
            <FormControlLabel control={
            <Checkbox defaultChecked  checked={isTrainer} onChange={handleCheckboxChange}/>} 
            label="Trainer" />
            <Box
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    '& > :not(style)': { m: 1 },
                }}
                >
                <TextField
                    helperText="Please enter your Weight"
                    id="demo-helper-text-aligned"
                    label="Weight"
                />
                <TextField
                    helperText="Please enter your Height "
                    id="demo-helper-text-aligned-no-helper"
                    label="Height"
                />
            </Box>
           <Box>
           <Typography id="track-false-slider" gutterBottom>
                Age
            </Typography>
            <Slider
                    aria-label="Always visible"
                    defaultValue={20}
                    getAriaValueText={valuetext}
                    step={1}
                    marks={marks}
                    valueLabelDisplay="on"
                />
            </Box> 
            
        
                
            <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              autoComplete="username"
              autoFocus
              variant="outlined"
              value={username} 
              onChange={e => setUsername(e.target.value)}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              variant="outlined"
              value={password} 
              onChange={e => setPassword(e.target.value)}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ py: 2, mt: 3, mb: 2 }}
              endIcon={<SendIcon />}
              onSubmit={handleSignup}
              
            >
              Sign Up
            </Button>
            <Button
              type="reset"
              fullWidth
              variant="outlined"
              sx={{ py: 2, mb: 2 }}
              startIcon={<KeyboardReturn />}
            >
              Return
            </Button>
            <Grid container justifyContent="space-between">
              <Grid item>
              </Grid>
              <Grid item>
              </Grid>
            </Grid>
          </Box>
        </Stack>
      </Grid>
        </Grid>
       
    )
    
}

export default Signup ;
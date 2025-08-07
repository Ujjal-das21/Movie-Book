package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.ScreenDTO;
import com.moviebook.moviebook.service.ScreenService;

@RestController
@RequestMapping("/api")
public class ScreenController {

    @Autowired
    ScreenService screenService;

    @PostMapping("/admin/theaters/{theaterId}/screens")

    public ResponseEntity<ScreenDTO> createScreen(@PathVariable Long theaterId,@RequestBody ScreenDTO screenDTO)
    {
        ScreenDTO createdScreen=screenService.createScreen(theaterId,screenDTO);
        return new ResponseEntity<>(createdScreen,HttpStatus.CREATED);
    }

    @PutMapping("/admin/screens/{screenId}")

    public ResponseEntity<ScreenDTO> updateScreen(@PathVariable Long screenId,@RequestBody ScreenDTO screenDTO)
    {
        ScreenDTO updatedScreen=screenService.updateScreen(screenId,screenDTO);
        return new ResponseEntity<>(updatedScreen,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/screens/{screenId}")

    public ResponseEntity<ScreenDTO> deleteScreen(@PathVariable Long screenId)
    {
        ScreenDTO deletedScreen=screenService.deleteScreen(screenId);
        return new ResponseEntity<>(deletedScreen,HttpStatus.OK);
    }

    @GetMapping("/public/theaters/{theaterId}/screens")

    public ResponseEntity<List<ScreenDTO>> getScreenByTheaterId(@PathVariable Long theaterId)
    {
        List<ScreenDTO> screenList=screenService.getScreenByTheaterId(theaterId);
        return new ResponseEntity<>(screenList,HttpStatus.OK);
    }

    @GetMapping("/public/screens/{screenId}")

    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable Long screenId)
    {
        ScreenDTO searchedScreen=screenService.findScreenById(screenId);
        return new ResponseEntity<>(searchedScreen,HttpStatus.OK);
    }


}

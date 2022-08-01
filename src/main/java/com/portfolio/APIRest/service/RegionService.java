/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Region;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface RegionService {

    public List<Region> getRegions();

    public Region getRegion(Long id);

    public Region saveRegion(Region region);

    public void deleteRegion(Long id);
}

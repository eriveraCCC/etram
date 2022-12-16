/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.space.request;

import com.ixxus.etram.confluence.model.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateSpaceRequest {

    private String key;
    private String name;
    private String type;
    private Description description;

}

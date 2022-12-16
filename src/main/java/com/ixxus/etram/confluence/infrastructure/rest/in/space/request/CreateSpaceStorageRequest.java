/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.space.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateSpaceStorageRequest {

    private String value;
    private String representation;

}

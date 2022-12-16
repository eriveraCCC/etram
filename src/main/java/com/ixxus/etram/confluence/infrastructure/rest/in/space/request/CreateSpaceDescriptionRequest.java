/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.in.space.request;

import com.ixxus.etram.confluence.model.Storage;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateSpaceDescriptionRequest {

    private Storage plain;

}

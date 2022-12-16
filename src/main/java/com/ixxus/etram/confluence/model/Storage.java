/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Storage {

    private String value;
    private String representation;
}

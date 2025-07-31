package com.example.back_gr.com;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskClass
{
    private int id_task=0;
    private String task_name;
    private String task_text;
    private String deadline;
    private boolean complite=false;


}

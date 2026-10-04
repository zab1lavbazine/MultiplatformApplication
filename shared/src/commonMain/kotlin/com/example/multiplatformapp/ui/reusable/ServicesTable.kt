package com.example.multiplatformapp.ui.reusable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageSquareMore
import com.composables.icons.lucide.Option
import com.example.multiplatformapp.model.MonitoredService


@Composable
fun ServicesTable (
    services: List<MonitoredService>,
    onCheck: (Long) -> Unit,
    onDelete: (Long) -> Unit,
) {

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableCell("Name", 1f)
            TableCell("URL", 2f)
            TableCell("Status", 1f)
            TableCell("Response", 1f)
            TableCell("Interval", 1f)
            TableCell("Action", 1f)
        }

        HorizontalDivider()

        services.forEach { service ->

            var expanded by remember {
                mutableStateOf(false)
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(service.name, 1f)
                TableCell(service.url, 2f)
                TableCell(service.status.toString(), 1f)

                TableCell(
                    service.responseTimeMillis?.let { "$it ms" } ?: "-",
                    1f
                )

                TableCell(
                    "${service.intervalMills / 1000}s",
                    1f
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Lucide.MessageSquareMore,
                            contentDescription = "Options",
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Check")
                            },
                            onClick = {
                                expanded = false
                                onCheck(service.id)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text("Delete")
                            },
                            onClick = {
                                expanded = false
                                onDelete(service.id)
                            }
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp)
    )
}
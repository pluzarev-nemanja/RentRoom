package com.example.rentproject.presentation.room_details

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MagnifierStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material.icons.outlined.Person4
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.rentproject.R
import com.example.rentproject.data.data_source.relations.RoomAndPerson
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoomDetailsScreen(
    room: Room?,
    navController: NavHostController,
    roomAndPerson: List<RoomAndPerson>,
    upsertPerson: (Person) -> Unit,
    upsertRoom: (Room) -> Unit,
    deletePerson: (Person) -> Unit,
    currency: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var person by remember {
        if (roomAndPerson.isNotEmpty()) mutableStateOf(roomAndPerson.first().person) else mutableStateOf<Person?>(
            null
        )
    }
    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf(false)
    }
    var phoneNum by remember {
        mutableIntStateOf(0)
    }
    var available by remember {
        mutableStateOf(
            if (room?.available == true) "Yes" else "No"
        )
    }
    var personName by remember {
        mutableStateOf(person?.personName)
    }
    var personSurname by remember {
        mutableStateOf(person?.personsLastName)
    }
    var personGender by remember {
        mutableStateOf(person?.gender)
    }
    var phone by remember {
        mutableStateOf(person?.phoneNumber)
    }

    val localContext = LocalContext.current


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Bed Details", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            BedDetails(
                paddingValues = paddingValues,
                room = room,
                available = available,
                upsertRoom = upsertRoom,
                localContext = localContext,
                currency = currency
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Divider(
                    thickness = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        5.dp
                    )
                )
                Text(
                    text = "Change details about your guest : ",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (person != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Guest name : ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(value = personName!!, onValueChange = {
                            personName = it
                        },
                            label = { Text("First name") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.PersonPinCircle,
                                    contentDescription = "person"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Guest surname : ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(value = personSurname!!,
                            onValueChange = {
                                personSurname = it
                            },
                            label = { Text("Last name") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.PersonPinCircle,
                                    contentDescription = "person"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Guest phone number : ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(value = phone.toString(), onValueChange = {
                            if (it.isNotEmpty())
                                phone = it.toInt()
                        },
                            label = { Text("Phone number") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.PhoneAndroid,
                                    contentDescription = "person"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Guest Gender : ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(value = if (personGender == true) "Male" else "Female",
                            onValueChange = {
                                personGender = it.toBoolean()
                            },
                            label = { Text("Gender") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Person4,
                                    contentDescription = "person"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            upsertPerson.invoke(
                                person!!.copy(
                                    personName = personName!!,
                                    personsLastName = personSurname!!,
                                    phoneNumber = phone!!,
                                    gender = personGender!!
                                )
                            )
                            Toast.makeText(localContext, "Persons data updated!", Toast.LENGTH_LONG)
                                .show()
                        }) {
                            Text(text = "Update Person")
                        }
                        Button(onClick = {
                            deletePerson.invoke(person!!)
                            person = null
                            available = "Yes"
                            upsertRoom.invoke(
                                room!!.copy(
                                    available = true,
                                )
                            )
                            Toast.makeText(localContext, "Person deleted!", Toast.LENGTH_LONG)
                                .show()
                        }) {
                            Text(text = "Delete Person")
                        }
                    }

                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Guest name : ",
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    OutlinedTextField(value = name, onValueChange = {
                                        name = it
                                    },
                                        label = { Text("First name") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(15.dp),
                                        trailingIcon = {
                                            Icon(
                                                imageVector = Icons.Outlined.PersonPinCircle,
                                                contentDescription = "person"
                                            )
                                        }
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Guest surname : ",
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    OutlinedTextField(value = surname, onValueChange = {
                                        surname = it
                                    },
                                        label = { Text("Last name") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(15.dp),
                                        trailingIcon = {
                                            Icon(
                                                imageVector = Icons.Outlined.PersonPinCircle,
                                                contentDescription = "person"
                                            )
                                        }
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Guest phone number : ",
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    OutlinedTextField(value = phoneNum.toString(), onValueChange = {
                                        phoneNum = it.toInt()
                                    },
                                        label = { Text("Phone number") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(15.dp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        trailingIcon = {
                                            Icon(
                                                imageVector = Icons.Outlined.PhoneAndroid,
                                                contentDescription = "person"
                                            )
                                        }
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Guest Gender : ",
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    OutlinedTextField(value = if (gender) "Male" else "Female",
                                        onValueChange = {
                                            gender = it.toBoolean()
                                        },
                                        label = { Text("Gender") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(15.dp),
                                        trailingIcon = {
                                            Icon(
                                                imageVector = Icons.Outlined.Person4,
                                                contentDescription = "person"
                                            )
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(onClick = {
                                if (room != null) {
                                    upsertPerson.invoke(
                                        Person(
                                            personId = 0,
                                            personName = name,
                                            personsLastName = surname,
                                            phoneNumber = phoneNum,
                                            gender = gender,
                                            roomId = room.roomId
                                        )
                                    )
                                    upsertRoom.invoke(
                                        room.copy(
                                            available = false
                                        )
                                    )
                                }

                                available = "No"
                            }) {
                                Text(text = "Add guest")
                            }
                        }

                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BedDetails(
    paddingValues: PaddingValues,
    room: Room?,
    available: String,
    upsertRoom: (Room) -> Unit,
    localContext: Context,
    currency: Boolean
) {
    var text by remember { mutableStateOf(room?.roomName) }
    val floorImage by remember {
        when (room?.floorId) {
            0 -> mutableIntStateOf(R.drawable.dole)
            1 -> mutableIntStateOf(R.drawable.prvi)
            else -> mutableIntStateOf(R.drawable.potkrovlje)
        }
    }
    var rent by remember {
        mutableStateOf(room?.rent)
    }

    var reservationPeriod by remember {
        mutableStateOf(room?.reservationPeriod)
    }
    var totalIncome by remember {
        mutableStateOf(
            room?.totalIncome
        )
    }
    val curr by remember {
        if(!currency) mutableStateOf("Euro") else mutableStateOf("Rsd")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp)
                .drawAnimatedBorder(
                    strokeWidth = 4.dp,
                    durationMillis = 2000,
                    shape = RoundedCornerShape(20.dp)
                ),
            shape = RoundedCornerShape(20.dp),
        ) {
            var magnifierCenter by remember {
                mutableStateOf(Offset.Unspecified)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            // Show the magnifier in the initial position
                            onDragStart = { magnifierCenter = it },
                            // Magnifier follows the pointer during a drag event
                            onDrag = { _, delta ->
                                magnifierCenter = magnifierCenter.plus(delta)
                            },
                            // Hide the magnifier when a user ends the drag movement.
                            onDragEnd = { magnifierCenter = Offset.Unspecified },
                            onDragCancel = { magnifierCenter = Offset.Unspecified },
                        )
                    }
                    .magnifier(
                        sourceCenter = {
                            if (magnifierCenter != Offset.Unspecified) magnifierCenter - Offset(
                                0f,
                                200f
                            ) else magnifierCenter
                        },
                        zoom = 3f,
                        style = MagnifierStyle(
                            size = DpSize(height = 120.dp, width = 150.dp),
                            cornerRadius = 20.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = floorImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
            }

        }
        Divider(
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                5.dp
            )
        )
        Text(
            text = "Change details about ${room?.roomName} : ",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.1F)),
                        startY = 0.0f,
                        endY = 400.0f
                    )
                )
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Bed name : ",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedTextField(
                        value = text.toString(), onValueChange = {
                            text = it
                        },
                        label = { Text("Bed Name") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        readOnly = true
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Bed availability : ",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedTextField(value = available, onValueChange = {
                    },
                        label = { Text("Availability") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        readOnly = true,
                        trailingIcon = {
                            if (available == "Yes")
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "check"
                                )
                            else
                                Icon(
                                    imageVector = Icons.Outlined.Dangerous,
                                    contentDescription = "not checked"
                                )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Daily rent : ",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedTextField(value = rent.toString(), onValueChange = {
                        if (it.isNotEmpty())
                            rent = it.toFloat()
                    },
                        label = { Text("Change price") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AttachMoney,
                                contentDescription = "money"
                            )
                        },
                        suffix = {
                            Text(text = curr)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Reservation period : ",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedTextField(value = reservationPeriod.toString(), onValueChange = { day ->
                        if (day.isNotEmpty())
                            reservationPeriod = day.toInt()
                    },
                        label = { Text("Change period") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = "money"
                            )
                        },
                        suffix = {
                            Text(text = " days")
                        }

                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total income : ",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedTextField(value = if (reservationPeriod != 0) {
                        (reservationPeriod?.let { rent?.div(it.toFloat()) }).toString()
                    } else "0", onValueChange = {
                        totalIncome = if (reservationPeriod != 0) {
                            (reservationPeriod?.let { rent?.div(it.toFloat()) })
                        } else 0F
                    },
                        label = { Text("Income") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AttachMoney,
                                contentDescription = "money"
                            )
                        },
                        suffix = {
                            Text(text = curr)
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        //update function for bed data
                        upsertRoom.invoke(
                            room!!.copy(
                                available = available == "Yes",
                                rent = rent!!,
                                reservationPeriod = reservationPeriod!!,
                                totalIncome = totalIncome!!
                            )
                        )
                        Toast.makeText(localContext, "Bed details updated!", Toast.LENGTH_LONG)
                            .show()
                    }) {
                        Text(text = "Update changes")
                    }
                }
            }
        }


    }
}

fun Modifier.drawAnimatedBorder(
    strokeWidth: Dp,
    shape: Shape,
    brush: (Size) -> Brush = {
        val gradientColors = listOf(
            Color(0xffcc8f00),
            Color(0xffe6e600),
            Color(0xffffff1a),
            Color(0xffcc8f00),
            Color(0xffffff66),
            Color(0xffcc8f00)
        )
        Brush.sweepGradient(gradientColors)
    },
    durationMillis: Int
) = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Modifier
        .clip(shape)
        .drawWithCache {
            val strokeWidthPx = strokeWidth.toPx()

            val outline: Outline = shape.createOutline(size, layoutDirection, this)

            val pathBounds = outline.bounds

            onDrawWithContent {
                // This is actual content of the Composable that this modifier is assigned to
                drawContent()

                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    // Destination

                    // We draw 2 times of the stroke with since we want actual size to be inside
                    // bounds while the outer stroke with is clipped with Modifier.clip

                    // 🔥 Using a maskPath with op(this, outline.path, PathOperation.Difference)
                    // And GenericShape can be used as Modifier.border does instead of clip
                    drawOutline(
                        outline = outline,
                        color = Color.Gray,
                        style = Stroke(strokeWidthPx * 2)
                    )

                    // Source
                    rotate(angle) {

                        drawCircle(
                            brush = brush(size),
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    restoreToCount(checkPoint)
                }
            }
        }
}
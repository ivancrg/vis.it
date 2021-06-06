package com.example.visit.destination;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.visit.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class DestinationFragment extends Fragment {
    private KonfettiView confetti;
    ImageView onDestination;
    TextView clickMe;
    private Shape.DrawableShape starShape;
    private Shape.DrawableShape carShape;
    private Shape.DrawableShape exploreShape;
    private Shape.DrawableShape locationShape;
    private Shape.DrawableShape airplaneShape;

    public DestinationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        confetti = view.findViewById(R.id.destinationFragmentConfetti);
        onDestination = view.findViewById(R.id.destinationFragmentOnDestination);
        clickMe = view.findViewById(R.id.destinationFragmentClickMe);

        // Shapes for confetti
        Drawable star = ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.star);
        starShape = new Shape.DrawableShape(star, true);
        Drawable car = ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.ic_travelling);
        carShape = new Shape.DrawableShape(car, true);
        Drawable explore = ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.ic_explore);
        exploreShape = new Shape.DrawableShape(explore, true);
        Drawable location = ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.ic_on_location);
        locationShape = new Shape.DrawableShape(location, true);
        Drawable airplane = ContextCompat.getDrawable(requireActivity().getApplicationContext(), R.drawable.ic_airplane);
        airplaneShape = new Shape.DrawableShape(airplane, true);

        startConfetti();

        onDestination.setOnClickListener(v -> {
            startConfetti();
        });

        clickMe.setOnClickListener(v -> {
            startConfetti();
        });

        return view;
    }

    private void startConfetti() {
        // Creates, configures and starts the confetti animation

        confetti.build()
                // Selecting the shapes' available colors
                .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN)
                // Selecting the shapes' available directions
                .setDirection(0.0, 359.0)
                // Selecting the shapes' speed interval
                .setSpeed(1f, 5f)
                // Fade out: enable/disable
                .setFadeOutEnabled(true)
                // Selecting the time it takes for a shape to disappear
                .setTimeToLive(2000L)
                // Selecting the shapes
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, starShape, carShape, exploreShape, locationShape, airplaneShape)
                // Selecting the shapes' size interval
                .addSizes(new Size(16, 35f))
                // Selecting the animation's initial position
                .setPosition(-50f, confetti.getWidth() + 50f, -50f, -50f)
                // Selecting the animation's duration and number of generated PPS
                .streamFor(300, 5000L);

        if ((Math.random() <= 0.5)) {
            animateVibrate(clickMe);
        } else {
            animateVibrate(onDestination);
        }
    }

    private void animateVibrate(View v) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(v, "rotation", 0f, 15f, 0f, -15f, 0f); // Rotate 15 degrees then -15 degrees and so on for one loop of rotation

        rotate.setRepeatCount(15); // Repeat the loop 15 times
        rotate.setDuration(200); // Animation play time 200 ms
        rotate.start();
    }
}